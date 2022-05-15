package commom;

import utils.getString;
import utils.isAccess;

import java.io.IOException;
import java.io.Serializable;

public class Menu  implements Serializable {
    private  static final  long serialVersionUID  = 1L;

    public static void MainMenu() throws IOException {
        boolean loop = true;
        String select = null;
        while (loop) {
            {
                System.out.println("=========欢迎来到登陆系统=========");
                System.out.println("\t\t" + "1 登陆系统");
                System.out.println("\t\t" + "9 退出系统");
            }
            select = getString.reading();
            switch (select) {
                case "1":
                    User user =  isAccess.getIs();
                    if(CheckData.checkData(user)){
                        loop = SeconMenu.SecondMenu(user);
                    }else {
                        System.out.println("---密码错误----");
                    }

                    break;
                case "9":
                    loop = false;
                    break;
                default:
                    System.out.println("---------------");
            }
        }


    }

}
