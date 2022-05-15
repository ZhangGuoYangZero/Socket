package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import commom.*;

public class server {
    private static ServerSocket sockets = null;
    private static Socket socket = null;
    private  static ConcurrentHashMap<String,String> concurrentHashMapM = new ConcurrentHashMap<>();
    static {
        concurrentHashMapM.put("1","100");
        concurrentHashMapM.put("2","100");
        concurrentHashMapM.put("3","100");
    }


    public  static boolean check(User user){
        String pwd = concurrentHashMapM.get(user.getId());
        if (pwd == null)
            return false;
        if(!pwd.equals(user.getPwd()))
            return false;
        return true;
    }



    //方法属于类，调用才触发
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        sockets = new ServerSocket(9999);
        boolean loop = true;
        ObjectInputStream objUser = null;
        ObjectOutputStream objMsg = null;
        Message message = null;
        ServerSend serverSend = new ServerSend();
        serverSend.start();

        while (loop) {
            socket = sockets.accept();
            try {
                try {
                    objUser = new ObjectInputStream(socket.getInputStream());
                    objMsg = new ObjectOutputStream(socket.getOutputStream());
                } catch (IOException e) {
                    throw new IOException("server端 IOE错误");
                }
                User user = null;
                try {
                    user = (User) objUser.readObject();
                } catch (IOException e) {
                    throw new IOException("Server--- USER类读取错误");
                } catch (ClassNotFoundException c) {
                    throw new ClassNotFoundException("Server--- 找不到这个类");
                }

                try {
                    if (check(user)) {
                        message = new Message();
                        message.setMsgType(MessageType.MESSAGE_LOGIN_SUCCESS);
                        objMsg.writeObject(message);
                        ServerThread serverThread = new ServerThread(socket, user.getId());
                        serverThread.start();
                        ManagerServerThread.addServerThread(user.getId(), serverThread);


                    } else {
                        message = new Message();
                        message.setMsgType(MessageType.MESSAGE_LOGIN_FAILD);
                        objMsg.writeObject(message);
                        System.out.println(user.getId() + "登陆失败...");
                        if (objUser != null)
                            objUser.close();
                        if (objMsg != null)
                            objMsg.close();
                        socket.close();
                    }
                } catch (IOException e) {
                    throw new IOException("Server--- MSG类写入错误");
                }
            } catch (ClassNotFoundException e) {
                throw new ClassNotFoundException(e.getMessage());
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }
        if (objUser != null)
            objUser.close();
        if (objMsg != null)
            objMsg.close();
        socket.close();
    }
}

class  ServerSend extends  Thread{
    @Override
    public void run() {
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            System.out.println("服务端群发消息！");
            String text = scanner.nextLine();
            if(text.equals("exit")) {
                System.out.println("群发退出！");
                loop =false;
                break;
            }
            Message message = new Message();
            message.setMsgType(MessageType.COMM_MSG);
            message.setContent(text);
            message.setSender("server");
            message.setGetSender("所有人");
            HashMap<String, ServerThread> hashMap = ManagerServerThread.getHashMap();
            for (ServerThread serverThread :hashMap.values()) {
                Socket socket = serverThread.getSocket();
                try {
                    ObjectOutputStream objMsg = new ObjectOutputStream(socket.getOutputStream());
                    objMsg.writeObject(message);
                    objMsg.flush();
                } catch (IOException e) {
                    System.out.println("服务的群发消息错误");
                    e.printStackTrace();
                }
            }

        }
    }
}


