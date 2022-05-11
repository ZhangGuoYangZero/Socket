package Scoket__;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Socket_Serve {

    @Test
    public   void  Serve() throws IOException {
        ServerSocket socket = new ServerSocket(9999);
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.write("等待链接....");
        printWriter.flush();
        Socket socketAccept = socket.accept();

        //输入信息  输出信息
        BufferedInputStream bfi =  new BufferedInputStream(socketAccept.getInputStream());
        BufferedOutputStream bfo = new BufferedOutputStream(socketAccept.getOutputStream());

        //试一试先写入
        String text =  "hello client";
        bfo.write(text.getBytes(StandardCharsets.UTF_8));
        bfo.flush();
        //可以告知写入完毕，如果关闭流就会调用自动关闭socket
        socketAccept.shutdownOutput();
        //
        printWriter.write("写入完毕,已经发送过去了");
        printWriter.flush();

        //在试一试读取
        byte[] bbf = new byte[256];
        int size;
        while ( (size = bfi.read(bbf)) != -1) {
            printWriter.write(new String(bbf, 0, size));
            printWriter.flush();
        }
        //可以告知读取完毕，如果关闭流就会调用自动关闭socket
        socketAccept.shutdownInput();

        //关闭
        printWriter.write("close");
        printWriter.flush();
        bfi.close();
        bfo.close();
        socket.close();

    }

}
