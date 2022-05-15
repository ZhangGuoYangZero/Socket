package commom;

import java.io.Serializable;

public class User implements Serializable {
    private static  final  long serialVersionUID = 1L;
    private  String id;
    private  String pwd;

    public User(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
