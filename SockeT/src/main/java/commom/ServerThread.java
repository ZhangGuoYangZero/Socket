package commom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public  class ServerThread extends Thread {
    private Socket socket = null;
    private String userId = null;

    public ServerThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {
        boolean loop = true;
        while (loop) {
            try {
                System.out.println(userId + " 正在链接....");
                ObjectInputStream objIMsg = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) objIMsg.readObject();
                Message msg1  = null;
                if(msg.getMsgType().equals(MessageType.GET_ONLINE_P)){
                    msg1 = new Message(msg.getGetSender(),msg.getSender(),ManagerServerThread.getOnlinePerson(),"");
                    msg1.setMsgType(MessageType.SEND_ONLINE_P);
                    Socket socket = ManagerServerThread.getServerThread(msg.getSender()).getSocket();
                    Write(socket,msg1);
                    System.out.println( userId + "要求了 查看在线用户列表");
                }else if( msg.getMsgType().equals(MessageType.END_get)){
                    msg1 = new Message();
                    msg1.setMsgType(MessageType.END_res);
                    Write(socket,msg1);
                    socket.shutdownOutput();
                    socket.shutdownInput();
                    socket.close();
                    loop = false;
                }else if(msg.getMsgType().equals(MessageType.hisper)){
                    System.out.println(msg.getSender() + "对" + msg.getGetSender() + "发送了私聊");
                    msg1 = new Message(msg.getSender(),msg.getGetSender(),msg.getContent(),"");
                    msg1.setMsgType(MessageType.hisper);
                    Socket socket = ManagerServerThread.getServerThread(msg.getGetSender()).getSocket();
                    Write(socket,msg1);
                }else if(msg.getMsgType().equals(MessageType.COMM_MSG)){
                    System.out.println('\n'+msg.getSender() +"群发了消息");
                    msg1 = new Message(msg.getSender(),"",msg.getContent(),"");
                    msg1.setMsgType(MessageType.COMM_MSG);
                    msg1.setGetSender("所有人");
                    HashMap<String, ServerThread> hashMap = ManagerServerThread.getHashMap();
                    for (ServerThread serverThread :hashMap.values()) {
                        Socket socket = serverThread.getSocket();
                        Write(socket,msg1);
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

    public  static  void  Write(Socket socket,Message msg1) throws IOException {
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
