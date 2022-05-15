package utils;
import commom.User;

import java.io.Serializable;
import  java.util.Scanner;

public class isAccess  implements Serializable {
    private  static final  long serialVersionUID  = 1L;

    private static String userId = null;
    private static String pwd = null;
    private static Scanner  scan = new Scanner(System.in);
    public static User getIs(){
        System.out.print("输入账号: ");
        userId = scan.nextLine();
        System.out.print("输入密码: ");
        pwd = scan.nextLine();
        return   new User(userId,pwd);
    }


}
