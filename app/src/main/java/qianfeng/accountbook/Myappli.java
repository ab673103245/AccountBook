package qianfeng.accountbook;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public class Myappli extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Bmob.initialize(this,"b70bf1999be9be6ce8ba60bbc7b7c10e");
        Bmob.initialize(this, "b70bf1999be9be6ce8ba60bbc7b7c10e");

    }
}
