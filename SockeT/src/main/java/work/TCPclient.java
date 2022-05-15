package work;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPclient {
    public  static  void main(String[] args){
        try {
            new TCPclient().client();
        } catch (IOException e) {
            System.out.println("EXCEPTION");
            e.printStackTrace();
        }
    }
    @Test
    public    void client() throws IOException {
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),9999);
        Writer writer = new OutputStreamWriter(socket.getOutputStream());
        writer.write("hl");
        //从缓存到管道里面
        writer.flush();
        socket.shutdownOutput();

        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = new byte[256];
        Integer size;
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("src/main/resources/xx.txt"));
        while((size = bufferedInputStream.read(bytes)) != -1){
            bufferedOutputStream.write(bytes,0,size);
            bufferedOutputStream.flush();
        }
        socket.shutdownInput();
        System.out.println("----完毕----");

        bufferedOutputStream.close();
        bufferedOutputStream.close();
        socket.close();
    }
}
