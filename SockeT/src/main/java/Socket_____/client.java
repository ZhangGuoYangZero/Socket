package Socket_____;

import org.junit.Test;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class client {
    @Test
    public  void  client() throws IOException {
        Socket socket  = new Socket(InetAddress.getLocalHost(),8888);

        System.out.println("---------------------");
        System.out.println(socket);
        System.out.println("---------------------");



        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("src/main/resources/t.jpg"));
        Integer size = 0;
        byte[] bytes = new byte[256];
        while ( (size = bufferedInputStream.read(bytes) )!= -1){
            bufferedOutputStream.write(bytes,0,size);
        }
        //flush在循环里面刷更快
        bufferedOutputStream.flush();
        socket.shutdownOutput();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
        String text = null;
        while ((text = bufferedReader.readLine()) != null){
            System.out.println(text);
        }
        socket.shutdownInput();


        bufferedReader.close();
        bufferedInputStream.close();
        bufferedOutputStream.close();
        socket.close();



    }
}
