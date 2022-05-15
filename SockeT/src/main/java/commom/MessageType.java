package commom;

import org.junit.Test;

import java.io.Serializable;

public interface MessageType extends Serializable {

    /*
    接口中每一个方法也是隐式抽象的,接口中的方法会被隐式的指定为 public abstract
    （只能是 public abstract，其他修饰符都会报错）。
    接口中可以含有变量，但是接口中的变量会被隐式的指定为 public static final 变量
    （并且只能是 public，用 private 修饰会报编译错误）。
    **/
    public  static  final  String MESSAGE_LOGIN_SUCCESS = "1";
    public  static  final  String MESSAGE_LOGIN_FAILD = "2";
    public  static  final  String COMM_MSG = "3";
    public  static  final  String GET_ONLINE_P = "4";
    public  static  final  String SEND_ONLINE_P = "5";
    public  static  final  String END_get = "7";
    public  static  final  String END_res = "8";
    public  static  final  String hisper = "9";

}

