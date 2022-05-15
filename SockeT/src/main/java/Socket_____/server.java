package Socket_____;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

    @Test
    public void Server() throws IOException {
        ServerSocket  serverSocket = new ServerSocket(8888);
        Socket sockets = serverSocket.accept();
        System.out.println("---------------------");
        System.out.println(sockets);
        System.out.println("---------------------");

        BufferedInputStream bufferedInputStream = new BufferedInputStream(sockets.getInputStream());
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream( new FileOutputStream("src/main/resources/b.jpg"));
        byte[] bytes = new byte[256];
        Integer size = null;

        while ((size = bufferedInputStream.read(bytes))!= -1){
            bufferedOutputStream.write(bytes,0,size);
            bufferedOutputStream.flush();
            //flush适合在循环里面刷
        }
        sockets.shutdownInput();


        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(sockets.getOutputStream(),"utf-8"));
        bufferedWriter.write("已经收到!");
        bufferedWriter.flush();
        sockets.shutdownOutput();





        bufferedWriter.close();
        bufferedOutputStream.close();
        bufferedInputStream.close();

        sockets.close();
        serverSocket.close();


    }
}
