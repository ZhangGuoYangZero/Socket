package commom;

import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ManagerServerThread {
    private  static HashMap<String ,ServerThread> hashMap = new HashMap<>();

    public  static  void   addServerThread(String userId, ServerThread serverThread){
        hashMap.put(userId,serverThread);
    }

    public   static  ServerThread getServerThread(String  userid){
        return  hashMap.get(userid);
    }

    public static HashMap<String, ServerThread> getHashMap() {
        return hashMap;
    }

    public  static  String  getOnlinePerson(){
        Set<Map.Entry<String,ServerThread>> set = hashMap.entrySet();
        Iterator<Map.Entry<String,ServerThread>> iterator = set.iterator();
        String text = "";
        while (iterator.hasNext()){
            text += iterator.next().getKey() + " ";
        }
        return  text;

    }
}
