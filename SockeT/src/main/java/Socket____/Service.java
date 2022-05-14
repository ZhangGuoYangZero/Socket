package Socket____;

import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Service {

    @Test
    public  void Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket sockets = serverSocket.accept();

        BufferedReader  bufferedReader = new BufferedReader( new InputStreamReader(sockets.getInputStream(),"utf-8"));
        String  text = null;
        if(!sockets.isInputShutdown()) {
            text = bufferedReader.readLine();
        }
        sockets.shutdownInput();

        System.out.println(text);

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(sockets.getOutputStream(),"utf-8"));
        text = "hello, client 你好";
        System.out.println("---------------");
        bufferedWriter.write(text);
        bufferedWriter.flush();
        sockets.shutdownOutput();

        bufferedReader.close();
        bufferedWriter.close();
        serverSocket.close();


    }


}
