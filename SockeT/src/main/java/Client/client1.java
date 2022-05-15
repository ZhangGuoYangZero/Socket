package Client;

import commom.Menu;

import java.io.IOException;

public class client1 {
    public static void main(String[] args) {
        try {
            Menu.MainMenu();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
