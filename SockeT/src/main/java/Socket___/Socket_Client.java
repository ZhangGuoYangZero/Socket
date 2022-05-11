package Socket___;

import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Socket_Client {

    @Test
    public   void  Client() throws IOException {
        PrintWriter pwr = new PrintWriter(System.out);
        pwr.println("开启访问。。");
        pwr.flush();

        Socket socket = new Socket("127.0.0.1",9999);

        //先来写入

        pwr.println("发送开始...");
        pwr.flush();
        String text = "hellow serve服务端";
        BufferedOutputStream bfo = new BufferedOutputStream(socket.getOutputStream());
        OutputStreamWriter o  = new OutputStreamWriter(bfo,"utf-8");

        o.write(text);
        o.flush();
        //可以告知写入完毕，如果关闭流就会调用自动关闭socket
        socket.shutdownOutput();

        pwr.println("接受开始...");
        pwr.flush();

        BufferedInputStream bfi = new BufferedInputStream(socket.getInputStream());
        InputStreamReader i = new InputStreamReader(bfi,"utf-8");
        char[] chars = new char[256];
        Integer size;
        while ((size = i.read(chars)) != -1){
            pwr.println(new String(chars,0,size));
            pwr.flush();
        }
        //可以告知读取完毕，如果关闭流就会调用自动关闭socket
        socket.shutdownInput();

        pwr.println("close...");
        pwr.flush();
        pwr.close();
        bfo.close();
        bfi.close();
    }

}
