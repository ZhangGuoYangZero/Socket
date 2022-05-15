package UDP;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Server {

    @Test
    public  void server() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(6666);

        byte[] bytes = new byte[256];
        DatagramPacket datagramPacket = new DatagramPacket(bytes,bytes.length);

        System.out.println("在这里卡住。。。。。。");
        datagramSocket.receive(datagramPacket);

        System.out.println("拆包");
        byte[] bytes1  = datagramPacket.getData();
        Integer size = datagramPacket.getLength();
        System.out.println( new String(bytes1,0,size,"utf-8"));
        String text = "你好客户端！";
        DatagramPacket datagramPacket1 = new DatagramPacket(text.getBytes(StandardCharsets.UTF_8),text.getBytes(StandardCharsets.UTF_8).length, InetAddress.getByName("127.0.0.1"),6665);
        datagramSocket.send(datagramPacket1);
        System.out.println("---发送完毕---");
        datagramSocket.close();
    }

}
