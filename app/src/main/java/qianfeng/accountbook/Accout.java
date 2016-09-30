package qianfeng.accountbook;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class Accout extends BmobObject {
    private String loginUsername;
    private String get;
    private String pain;
    private String comm;



    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    public String getPain() {
        return pain;
    }

    public void setPain(String pain) {
        this.pain = pain;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    @Override
    public String toString() {
        return "Accout{" +
                "loginUsername='" + loginUsername + '\'' +
                ", get='" + get + '\'' +
                ", pain='" + pain + '\'' +
                ", comm='" + comm + '\'' +
                '}';
    }
}
