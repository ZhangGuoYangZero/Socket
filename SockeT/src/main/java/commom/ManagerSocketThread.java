package commom;

import org.junit.Test;

import java.net.Socket;
import java.util.HashMap;

public class ManagerSocketThread {

    private static HashMap<String,ClientThread>  hashMap= new HashMap<>();

    public static HashMap<String, ClientThread> getHashMap() {
        return hashMap;
    }

    public static ClientThread getClientTHread(String userID){
       return hashMap.get(userID);
    }

    public static void  addClientThread(String userId,ClientThread clientThread){
        hashMap.put(userId,clientThread);
    }


}
