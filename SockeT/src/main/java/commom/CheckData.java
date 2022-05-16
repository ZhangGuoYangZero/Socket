package commom;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class CheckData {

    private static boolean b = false;

    public static boolean checkData(User user) throws IOException {
        Socket socketClient = new Socket(InetAddress.getByName("127.0.0.1"), 9999);

        //如何把数据写入到里面啊？ -- 通过对象流发送user类
        ObjectOutputStream objUser = null;
        ObjectInputStream objMsg = null;
        try {
            objUser = new ObjectOutputStream(socketClient.getOutputStream());
            objMsg = new ObjectInputStream(socketClient.getInputStream());
        } catch (IOException e) {
            throw new IOException("创建对象流有问题!");
        }
        try {
            try {
                objUser.writeObject(user);
            } catch (IOException e) {
                throw new IOException("user对象写入流有问题");
            }

            try {
                Message msg = (Message) objMsg.readObject();
                if (msg.getMsgType().equals(MessageType.MESSAGE_LOGIN_SUCCESS)) {

                    ClientThread clientThread = new ClientThread(socketClient, user.getId());
                    clientThread.start();
                    ManagerSocketThread.addClientThread(user.getId(), clientThread);
                    b = true;
                } else{
                    b = false;
                    objMsg.close();
                    objUser.close();
                    socketClient.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new IOException("msg对象读取有问题");
            }

        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return b;


    }

}


class ClientThread extends Thread {
    private Socket socketClient = null;
    private  String useID;

    public ClientThread(Socket socketClient ,String useID) {
        this.socketClient = socketClient;
        this.useID = useID;
    }

    public Socket getSocketClient() {
        return socketClient;
    }

    @Override
    public void run() {
        boolean loop = true;
        while (loop) {
            try {
                ObjectInputStream objIMsg = new ObjectInputStream(socketClient.getInputStream());
//                System.out.println("-----客户端(" + useID + ")在接受数据.......");
                Message msg = (Message) objIMsg.readObject();

                if(msg.getMsgType().equals(MessageType.SEND_ONLINE_P)){
                    String[] userId = msg.getContent().split(" ");
                    for (String s :userId) {
                        System.out.println('\n'+"用户: " + s);
                    }

                }else if(msg.getMsgType().equals(MessageType.END_res)){
                    socketClient.shutdownOutput();
                    socketClient.shutdownInput();
                    socketClient.close();
                    loop = false;
                    ManagerSocketThread.getHashMap().remove(useID);

                }else if (msg.getMsgType().equals(MessageType.hisper)){
                    System.out.print('\n' + msg.getSender() + "对" + useID + "说:" + msg.getContent()+"\n");
                }else if(msg.getMsgType().equals(MessageType.COMM_MSG)){
                    System.out.println('\n'+msg.getSender() + "对" + msg.getGetSender()+ "说:" +  msg.getContent());
                }else if(msg.getMsgType().equals(MessageType.FILE_SEND)){
                    System.out.println('\n'+msg.getSender() + "对" + msg.getGetSender()+ "发送了" +  msg.getContent());
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(msg.getbytes());
                    FileOutputStream fio = new FileOutputStream("SockeT/src/main/resources/a.jpg");
                    int size;
                    byte[] bytes =new byte[256];
                    while ((size = byteArrayInputStream.read(bytes))!=-1){
                        fio.write(bytes,0,size);
                        fio.flush();
                    }
                    fio.close();
                    byteArrayInputStream.close();
                }


            } catch (IOException e) {
                System.out.println("客户端线程有问题");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("客户端没有发现类");
                e.printStackTrace();
            }
        }
    }
}
