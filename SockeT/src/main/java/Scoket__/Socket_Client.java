package Scoket__;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Socket_Client {

    @Test
    public   void  Client() throws IOException {
        PrintWriter pwr = new PrintWriter(System.out);
        pwr.write("开启访问。。");
        pwr.flush();

        Socket socket = new Socket("127.0.0.1",9999);

        //先来写入

        pwr.write("发送开始...");
        pwr.flush();
        String text = "hellow serve";
        BufferedOutputStream bfo = new BufferedOutputStream(socket.getOutputStream());
        bfo.write(text.getBytes(StandardCharsets.UTF_8));
        bfo.flush();
        //可以告知写入完毕，如果关闭流就会调用自动关闭socket
        socket.shutdownOutput();

        pwr.write("接受开始...");
        pwr.flush();

        BufferedInputStream bfi = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = new byte[256];
        Integer size;
        while ((size = bfi.read(bytes))!= -1){
            pwr.write( new String(bytes,0,size));
            pwr.flush();
        }
        //可以告知读取完毕，如果关闭流就会调用自动关闭socket
        socket.shutdownInput();

        pwr.write("close...");
        pwr.flush();
        bfo.close();
        bfi.close();
    }

}
