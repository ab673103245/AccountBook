package qianfeng.accountbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import qianfeng.accountbook.register.Register_Activity;
import qianfeng.accountbook.viewPager.ViewPagerActivity;

public class MainActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = ((EditText) findViewById(R.id.et_username));
        et_password = ((EditText) findViewById(R.id.et_password));
        btn_login = ((Button) findViewById(R.id.btn_login));
    }

    public void login(View view) {  // 登录(数据库的查找)

//

        final String et_us = et_username.getText().toString();
        String et_pa = et_password.getText().toString();


        // 先查询数据库
        BmobQuery<Login> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username",et_us);
        bmobQuery.addWhereEqualTo("password",et_pa);

        bmobQuery.findObjects(new FindListener<Login>() {
            @Override
            public void done(List<Login> list, BmobException e) {
                if(e == null)
                {
                  if(list != null && list.size() == 1)
                  {
                      Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
//                      ProgressBar progressBar = new ProgressBar(MainActivity.this);
//                      progressBar.invalidate();
                      Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                      intent.putExtra("login_username",et_us);
                      startActivity(intent);
                      overridePendingTransition(R.anim.enter,R.anim.exit);
                      finish();
                  }
                }else
                {
                    Log.d("google-my:", "done: 连接不成功");
                }
            }
        });

    }

    public void cancel(View view) { // 取消(输入数据的重置)

    }

    public void register(View view) { // 注册

        Intent intent = new Intent(this, Register_Activity.class);
        startActivity(intent);
    }

//    public void add(View view) {
//        // 添加数据
//        Login login = new Login();
//        login.setUsername("lisi");
//        login.setPassword("12");
//        login.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e == null)
//                {
//                    Log.d("google-my:", "done: 保存成功");
//                }else
//                {
//                    Log.d("google-my:", "done: 添加失败");
//                }
//            }
//        });
//    }



}
