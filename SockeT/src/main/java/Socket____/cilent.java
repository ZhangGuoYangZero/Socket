package Socket____;

import org.junit.Test;

import java.io.*;
import java.net.Socket;

public class cilent {

    @Test
    public  void client() throws IOException {
        Socket socket  = new Socket("127.0.0.1",9999);

        BufferedWriter bufferedWriter =  new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"utf-8"));
        String text = " hellow  server 你好";
        if(!socket.isOutputShutdown()){
            bufferedWriter.write(text);
            bufferedWriter.flush();
        }
            socket.shutdownOutput();


        //这个isxxx  就跟文件eof 是一样的
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
        if(!socket.isInputShutdown()) {
            text = bufferedReader.readLine();
        }
        socket.shutdownInput();
        System.out.println(text);

        bufferedReader.close();
        bufferedWriter.close();




    }
}
