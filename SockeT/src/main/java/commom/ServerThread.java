package commom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ServerThread extends Thread {
    private Socket socket = null;
    private String userId = null;
    private ConcurrentHashMap<String, String> hashMap;

    public ServerThread(Socket socket, String userId, ConcurrentHashMap<String, String> hashMap) {
        this.socket = socket;
        this.userId = userId;
        this.hashMap = hashMap;
    }

    @Override
    public void run() {
        boolean loop = true;
        while (loop) {
            try {
                System.out.println(userId + " 正在链接....");
                ObjectInputStream objIMsg = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) objIMsg.readObject();
                Message msg1 = null;
                if (msg.getMsgType().equals(MessageType.GET_ONLINE_P)) {
                    msg1 = new Message(msg.getGetSender(), msg.getSender(), ManagerServerThread.getOnlinePerson(), "");
                    msg1.setMsgType(MessageType.SEND_ONLINE_P);
                    Socket socket = ManagerServerThread.getServerThread(msg.getSender()).getSocket();
                    Write(socket, msg1);
                    System.out.println(userId + "要求了 查看在线用户列表");
                } else if (msg.getMsgType().equals(MessageType.END_get)) {
                    msg1 = new Message();
                    msg1.setMsgType(MessageType.END_res);
                    Write(socket, msg1);
                    socket.shutdownOutput();
                    socket.shutdownInput();
                    socket.close();
                    loop = false;
                } else if (msg.getMsgType().equals(MessageType.hisper)) {
                    boolean True = true;
                    if (hashMap.get(msg.getGetSender()) == null) {
                        msg1 = new Message("server", msg.getSender(), "没有这个人", "");
                        msg1.setMsgType(MessageType.hisper);
                        Socket socket = ManagerServerThread.getServerThread(msg.getSender()).getSocket();
                        Write(socket, msg1);
                        continue;
                    }
                    if (ManagerServerThread.getServerThread(msg.getGetSender()) == null)
                        True = false;

                    if (True) {
                        System.out.println(msg.getSender() + "对" + msg.getGetSender() + "发送了私聊");
                        Socket socket = ManagerServerThread.getServerThread(msg.getGetSender()).getSocket();
                        Write(socket, msg);
                    } else {
                        System.out.println(msg.getSender() + "对" + msg.getGetSender() + "发送了私聊" + "但" + msg.getGetSender() + "不在线");
                        CheckAndSendToLOST cst = new CheckAndSendToLOST(msg);
                        cst.start();
                    }


                } else if (msg.getMsgType().equals(MessageType.COMM_MSG)) {
                    System.out.println('\n' + msg.getSender() + "群发了消息");
                    msg1 = new Message(msg.getSender(), "", msg.getContent(), "");
                    msg1.setMsgType(MessageType.COMM_MSG);
                    msg1.setGetSender("所有人");
                    HashMap<String, ServerThread> hashMap = ManagerServerThread.getHashMap();
                    for (ServerThread serverThread : hashMap.values()) {
                        Socket socket = serverThread.getSocket();
                        Write(socket, msg1);
                    }

                } else if (msg.getMsgType().equals(MessageType.FILE_SEND)) {
                    boolean True = true;
                    if (hashMap.get(msg.getGetSender()) == null) {
                        msg1 = new Message("server", msg.getSender(), "没有这个人", "");
                        msg1.setMsgType(MessageType.hisper);
                        Socket socket = ManagerServerThread.getServerThread(msg.getSender()).getSocket();
                        Write(socket, msg1);
                        continue;
                    }
                    if (ManagerServerThread.getServerThread(msg.getGetSender()) == null)
                        True = false;


                    if (True) {
                        System.out.println("\n" + msg.getSender() + "对" + msg.getGetSender() + "发送了文件");
                        ObjectOutputStream objMsg = new ObjectOutputStream(ManagerServerThread.getServerThread(msg.getGetSender()).getSocket().getOutputStream());
                        objMsg.writeObject(msg);
                        objMsg.flush();
                    } else {
                        System.out.println(msg.getSender() + "对" + msg.getGetSender() + "发送了文件" + "但" + msg.getGetSender() + "不在线");
                        CheckAndSendToLOST cst = new CheckAndSendToLOST(msg);
                        cst.start();
                    }
                }

            } catch (IOException e) {
                System.out.println("服务端线程问题");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("服务端线程问题 -- 没有发现类");
                e.printStackTrace();
            }
        }
        ManagerServerThread.getHashMap().remove(userId);
        System.out.println(userId + "退出了系统");
    }

    public static void Write(Socket socket, Message msg1) throws IOException {
        ObjectOutputStream objMsg = new ObjectOutputStream(socket.getOutputStream());
        objMsg.writeObject(msg1);
        objMsg.flush();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}


class CheckAndSendToLOST extends Thread {
    private Message msg = null;

    public CheckAndSendToLOST(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        while (ManagerServerThread.getServerThread(msg.getGetSender()) == null) ;
        Socket socket = ManagerServerThread.getServerThread(msg.getGetSender()).getSocket();
        try {
            ObjectOutputStream objMsg = new ObjectOutputStream(socket.getOutputStream());
            objMsg.writeObject(msg);
            objMsg.flush();
        } catch (IOException e) {
            System.out.println("上线发送线程有问题");
            e.printStackTrace();
        }
    }
}
