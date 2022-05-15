package commom;

import utils.getString;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

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


    public static boolean SecondMenu(User user) {
        SeconMenu.user = user;
        while (b) {
            {
                System.out.println("=====  欢迎 用户(" + user.getId() + ")");
                System.out.println("\t" + " 1. 显示用户在线列表");
                System.out.println("\t" + " 2.  群发消息");
                System.out.println("\t" + " 3.  私聊信息");
                System.out.println("\t" + " 4.  发送信息");
                System.out.println("\t" + " 5.  退出");
                System.out.print("请输入你的选择: ");
            }
            String select = getString.reading();
            switch (select) {
                case "1":
                    sendOnLine();
                    break;
                case "2":
                    System.out.println("\t" + " 2.  群发消息");
                    break;
                case "3":
                    System.out.println("\t" + " 3.  私聊信息");
                    break;
                case "4":
                    System.out.println("\t" + " 4.  发送信息");
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
