package UDP;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

public class client {
    @Test
    public  void cilent() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(6665);

        String text = "hellow serve 你好!";
        DatagramPacket datagramPacket = new DatagramPacket(text.getBytes(StandardCharsets.UTF_8),text.getBytes(StandardCharsets.UTF_8).length,InetAddress.getLocalHost(),6666);


        datagramSocket.send(datagramPacket);
        System.out.println("-------发送完毕---------");


        byte[] bytes = new byte[256];
        DatagramPacket datagramPacket1 = new DatagramPacket(bytes,bytes.length);
        datagramSocket.receive(datagramPacket1);
        System.out.println("-------拆包---------");
        byte[] bytes1 = datagramPacket1.getData();
        Integer size = datagramPacket1.getLength();
        System.out.println( new String(bytes1,0,size,"utf-8"));

        datagramSocket.close();
    }
}
