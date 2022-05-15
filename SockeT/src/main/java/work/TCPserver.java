package work;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPserver {
    public static void main(String[] args) {
        try {
            new TCPserver().server();
        } catch (IOException e) {
            System.out.println("EXCEPTION");
            e.printStackTrace();
        }
    }
    @Test
    public  void server() throws IOException {
        ServerSocket Ssocket = new ServerSocket(9999);
        Socket socket = Ssocket.accept();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = new byte[256];
        System.out.println("-----读取输入-----");
        Integer size = null;
        String text = "";
        while ((size = bufferedInputStream.read(bytes)) != -1)
            text += new String(bytes, 0, size);
        socket.shutdownInput();
        BufferedInputStream bufferedInputStream1;
        String path = null;
        if (text.equals("sg"))
            path = "SockeT/src/main/resources/sg.txt";
        else if (text.equals("sh"))
            path = "SockeT/src/main/resources/sh.txt";
        else
            path = "SockeT/src/main/resources/hl.txt";
        InputStream inputStream = new FileInputStream(path);
        byte[] bytes1 = new byte[256];
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        while ((size = inputStream.read(bytes1)) != -1) {
            bufferedOutputStream.write(bytes1, 0, size);
            bufferedOutputStream.flush();
        }
        socket.shutdownOutput();
        System.out.println("--发送完毕--------");


        bufferedOutputStream.close();
        bufferedOutputStream.close();
        inputStream.close();
        socket.close();
        Ssocket.close();
    }
}
