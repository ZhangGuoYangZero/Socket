package Socket___;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Socket_Serve {

    @Test
    public   void  Serve() throws IOException {
        ServerSocket socket = new ServerSocket(9999);
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.println("等待链接....");
        printWriter.flush();
        Socket socketAccept = socket.accept();

        //输入信息  输出信息
        BufferedInputStream bfi =  new BufferedInputStream(socketAccept.getInputStream());
        BufferedOutputStream bfo = new BufferedOutputStream(socketAccept.getOutputStream());
        InputStreamReader i = new InputStreamReader( bfi,"utf-8");
        OutputStreamWriter o = new OutputStreamWriter(bfo,"utf-8");

        //试一试先写入
        String text =  "hello client客户端";
        o.write(text);
        o.flush();
        //可以告知写入完毕，如果关闭流就会调用自动关闭socket
        socketAccept.shutdownOutput();
        //
        printWriter.println("写入完毕,已经发送过去了");
        printWriter.flush();

        //在试一试读取
        char[] chars = new char[256];
        int size;
        while ( (size = i.read(chars)) != -1) {
            printWriter.println(new String(chars, 0, size));
            printWriter.flush();
            
        }
        //可以告知读取完毕，如果关闭流就会调用自动关闭socket
        socketAccept.shutdownInput();

        //关闭
        printWriter.println("close");
        printWriter.flush();
        printWriter.close();
        bfi.close();
        bfo.close();
        socket.close();


    }

    @Test
    public  void  test(){
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.println("hh");
        printWriter.flush();
    }

}
