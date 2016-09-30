package qianfeng.accountbook.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import qianfeng.accountbook.Login;
import qianfeng.accountbook.MainActivity;
import qianfeng.accountbook.R;

public class Register_Activity extends AppCompatActivity {

    private EditText et_reg_username;
    private EditText et_reg_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        et_reg_username = ((EditText) findViewById(R.id.et_reg_username));
        et_reg_password = ((EditText) findViewById(R.id.et_reg_password));

    }

    public void btn_reg(View view) { // 注册
        final String et_reg_us = et_reg_username.getText().toString();
        final String et_reg_pa = et_reg_password.getText().toString();

        if (et_reg_us.length() > 0 && et_reg_us != null && et_reg_pa.length() > 0 && et_reg_pa != null)
        {
            // 现在要跟网络数据库里面的username进行比对，如果没有，才可以注册

            BmobQuery<Login> bmobQuery = new BmobQuery<>();
            bmobQuery.addWhereEqualTo("username",et_reg_us);

            bmobQuery.findObjects(new FindListener<Login>() {
                @Override
                public void done(List<Login> list, BmobException e) {
                    if(e == null) // 连接成功,在回调方法中可以直接更新UI
                    {
                        if(list == null || list.size() == 0)
                        {
                            Login login = new Login();
                            login.setUsername(et_reg_us);
                            login.setPassword(et_reg_pa);
                            login.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if(e == null)
                                    {
                                        // 保存成功，并跳转到到登录页
                                        Toast.makeText(Register_Activity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Register_Activity.this, MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.enter,R.anim.exit);
                                        finish();
                                    }else
                                    {
                                        Log.d("google-my:", "done: 保存不成功");
                                    }
                                }
                            });

                        }else
                        {
                            Log.d("google-my:", "done: 注册不成功");
                        }
                    }else
                    {
                        Log.d("google-my:", "done: 查询不成功");
                    }
                }
            });
        }else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("用户名或密码不能为空");
            builder.setPositiveButton("确定",null);
            builder.create().show();
        }
    }
}
