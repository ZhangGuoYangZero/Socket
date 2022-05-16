package commom;

import utils.getString;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SeconMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    private static boolean b = true;
    private static User user = null;


    public static void sendOnLine() {
        Message msg = new Message(user.getId(), "server", "", "");
        msg.setMsgType(MessageType.GET_ONLINE_P);
        Socket socket = ManagerSocketThread.getClientTHread(user.getId()).getSocketClient();
        try {
            ObjectOutputStream objMsg = new ObjectOutputStream(socket.getOutputStream());
            objMsg.writeObject(msg);

        } catch (IOException e) {
            System.out.println("客户端，拉取请求错误");
            e.printStackTrace();
        }
    }

    public static void senEnd() {
        Message msg = new Message(user.getId(), "server", "", "");
        msg.setMsgType(MessageType.END_get);
        Socket socket = ManagerSocketThread.getClientTHread(user.getId()).getSocketClient();
        try {
            ObjectOutputStream objMsg = new ObjectOutputStream(socket.getOutputStream());
            objMsg.writeObject(msg);

        } catch (IOException e) {
            System.out.println("客户端，请求结束错误");
            e.printStackTrace();
        }
    }

    public static void sendAll() {
        Message msg = new Message();
        msg.setMsgType(MessageType.COMM_MSG);
        msg.setSender(user.getId());
        System.out.println("群发什么？");
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        msg.setContent(text);
        try {
            ObjectOutputStream objMsg = new ObjectOutputStream(ManagerSocketThread.getClientTHread(user.getId()).getSocketClient().getOutputStream());
            objMsg.writeObject(msg);
        } catch (IOException e) {
            System.out.println("群发错误");
            e.printStackTrace();
        }
    }


    public static void hsiper() {
        Scanner scanner = new Scanner(System.in);
        String text;
        do {
            text = scanner.nextLine();
        } while (text.length() == 0);
        System.out.print('\n' + user.getId() + "对" + text + "说:");
        String TEXT = scanner.nextLine();
        Socket socketClient = ManagerSocketThread.getClientTHread(user.getId()).getSocketClient();
        Message msg = new Message(user.getId(), text, TEXT, "");
        msg.setMsgType(MessageType.hisper);
        try {
            ObjectOutputStream objMsg = new ObjectOutputStream(socketClient.getOutputStream());
            objMsg.writeObject(msg);
            objMsg.flush();

        } catch (IOException e) {
            System.out.println("私聊客户端A出错");
            e.printStackTrace();
        }

    }

    public static  void sendFile(){
        Scanner scanner = new Scanner(System.in);
        String OtherID;
        do {
            OtherID = scanner.nextLine();
        } while (OtherID.length() == 0);
        System.out.println("请输入想发送的文件:");
        String File = scanner.nextLine();
        try {
            FileInputStream fi = new FileInputStream("SockeT/src/main/resources/"+File);
            byte[] bytes = new byte[256];
            Integer size;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while ( (size = fi.read(bytes)) != -1){
                byteArrayOutputStream.write(bytes,0,size);
            }
            fi.close();
            ObjectOutputStream objMsg = new ObjectOutputStream(ManagerSocketThread.getClientTHread(user.getId()).getSocketClient().getOutputStream());
            Message msg = new Message();
            msg.setMsgType(MessageType.FILE_SEND);
            msg.setSender(user.getId());
            msg.setGetSender(OtherID);
            msg.setContent(File);
            msg.setbytes(byteArrayOutputStream.toByteArray());
            objMsg.writeObject(msg);
            objMsg.flush();
            byteArrayOutputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("发送文件路径错误");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("发送文件读取错误");
            e.printStackTrace();
        }
    }


    public static boolean SecondMenu(User user) {
        SeconMenu.user = user;
        while (b) {
            {
                System.out.println("=====  欢迎 用户(" + user.getId() + ")");
                System.out.println("\t" + " 1. 显示用户在线列表");
                System.out.println("\t" + " 2.  群发消息");
                System.out.println("\t" + " 3.  私聊信息");
                System.out.println("\t" + " 4.  发送文件");
                System.out.println("\t" + " 5.  退出");
                System.out.print("请输入你的选择: ");
            }
            String select = getString.reading();
            switch (select) {
                case "1":
                    sendOnLine();
                    break;
                case "2":
                    sendAll();
                    break;
                case "3":
                    sendOnLine();
                    System.out.println("\t" + "请输入想私聊的对象");
                    hsiper();
                    break;
                case "4":
                    sendOnLine();
                    System.out.println("\t" + "请输入想发送文件的对象");
                    sendFile();
                    break;
                case "5":
                    senEnd();
                    b = false;
                    break;
            }

        }
        return false;
    }

}
