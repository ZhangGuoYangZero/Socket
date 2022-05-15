package UDP;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Client__ {
    @Test
    public  void client() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(8887);
        String text = "gan";
        DatagramPacket datagramPacket = new DatagramPacket(text.getBytes(StandardCharsets.UTF_8),text.getBytes(StandardCharsets.UTF_8).length, InetAddress.getByName("127.0.0.1"),8888);
        datagramSocket.send(datagramPacket);

        System.out.println("---发送完毕a---");

        byte[] bytes = new byte[256];
        datagramPacket = new DatagramPacket(bytes,bytes.length);
        datagramSocket.receive(datagramPacket);
        byte[] bytes1 =  datagramPacket.getData();
        Integer size = datagramPacket.getLength();
        System.out.println( new String(bytes1,0,size,"utf-8"));



    }
}
