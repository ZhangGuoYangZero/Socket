import org.junit.jupiter.api.Test;

import  java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressClassTest {
    @Test
    public  void test() throws UnknownHostException {
        //获取本机的INET对象
        InetAddress i = InetAddress.getLocalHost();
        System.out.println(i);// 名字/ip地址
        System.out.println(i.getHostName());//名字
        new String(i.getAddress()).toString();//返回地址，字节数组形式
        System.out.println(i.getHostAddress());//地址
        System.out.println(i.getClass());
    }

    @Test
    public void test1() throws UnknownHostException{
        //根据域名获取InetAdderss对象
        InetAddress i = InetAddress.getByName("www.baidu.com");
        //根据inet对象获取ip
        System.out.println(i.getHostAddress());
        //根据inet对象获取域名
        System.out.println(i.getHostName());
    }

}

