package qianfeng.accountbook;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class Login extends BmobObject {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
