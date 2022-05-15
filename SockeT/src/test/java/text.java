import Client.client1;
import Server.server;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import commom.*;



public class text {
    public static void main(String[] args) {
        try {
            new text().test();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public  void test() throws FileNotFoundException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("src\\main\\resources\\sg.txt"));

    }

    @Test
    public  void testClient1(){
        client1.main(null);
    }

    @Test
    public  void testServer(){
        try {
            server.main(null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
