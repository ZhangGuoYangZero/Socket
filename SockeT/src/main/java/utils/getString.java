package utils;
import  java.util.Scanner;

public class getString {
    private  static final  long serialVersionUID  = 1L;
    private  static Scanner scan =  new Scanner(System.in);

    public static String  reading(){
        return  scan.nextLine().charAt(0) + "";
    }


}
