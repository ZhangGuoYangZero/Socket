package Socket_;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Socket_serve {
    public static void main(String[] args) throws IOException {
        try{
        waitSocket();
        }catch ( IOException io){
            throw  new IOException("生成异常");
        }
    }

    private static void  waitSocket() throws IOException {
        //serve socket 还是开启等待
        //9999端口没有被占用
        ServerSocket socket = new ServerSocket(9999);
       //等待链接，链接到就返回对象，没有就阻塞
        Socket socketAccept =  socket.accept();

        InputStream inputStream = socketAccept.getInputStream();

        System.out.println("链接成功");

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
        char[] Bufs = new char[256];
        int size;
        while ((size = inputStreamReader.read(Bufs,0,Bufs.length))!= -1)
            System.out.println(new String(Bufs,0,size));
        //后开先关
        //关闭流
        inputStreamReader.close();
        //关闭Socket池
        socketAccept.close();
        //关闭端口监听
        socket.close();


    }
}
