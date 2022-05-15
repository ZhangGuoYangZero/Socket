package UDP;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Server__ {
    @Test
    public void server() throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(8888);
        byte[] bytes = new byte[256];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
        datagramSocket.receive(datagramPacket);
        System.out.println("------接受完毕-------");

        byte[] bytes1 = datagramPacket.getData();
        Integer size = datagramPacket.getLength();
        String text = new String(bytes1, 0, size, "utf-8");
        if (text.equals("四大名著")) {
            text = "西游记" + '\t'
                    + "红楼梦" + '\t' +
                    "水浒传" + '\t' +
                    "三国演义" + '\t';
        } else {
            text = "what?发的什么？";
        }

        datagramPacket = new DatagramPacket(text.getBytes(StandardCharsets.UTF_8), text.getBytes(StandardCharsets.UTF_8).length, InetAddress.getByName("127.0.0.1"), 8887);
        datagramSocket.send(datagramPacket);
        System.out.println("--------发送完毕");

        datagramSocket.close();


    }

}
