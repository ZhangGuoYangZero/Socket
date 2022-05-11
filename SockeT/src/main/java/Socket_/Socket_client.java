package Socket_;

import jdk.net.Sockets;

import java.io.*;
import java.net.Socket;

public class Socket_client {

    public static void main(String[] args) throws IOException {
        try {
            vivst();
        } catch (IOException e) {
            throw  new IOException("链接失败");
        }
    }

    private static void vivst() throws IOException {
        //访问 本机的是9999端口
        Socket socket =  new Socket("127.0.0.1",9999);

        //OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(),"utf-8");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new BufferedOutputStream(socket.getOutputStream()),"utf-8");
        String text = "hello scoket";
        outputStreamWriter.write(text);
        outputStreamWriter.flush();
        System.out.println("发送完毕!");
        outputStreamWriter.close();
        socket.close();
    }

}
