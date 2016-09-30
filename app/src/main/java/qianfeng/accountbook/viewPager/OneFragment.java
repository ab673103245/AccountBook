package qianfeng.accountbook.viewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import qianfeng.accountbook.Accout;
import qianfeng.accountbook.MyBroadcastReceiver.MyBroadcastReceiver;
import qianfeng.accountbook.R;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class OneFragment extends Fragment {

    private EditText et_get;
    private EditText et_pain;
    private EditText et_comm;
    private String login_username;
    private MyBroadcastReceiver myBroadcastReceiver;
    //    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onAttach(Context context) { // 在这里会与宿主Activity进行绑定
        super.onAttach(context);
    }

    @Override
    public void onStart() {
        super.onStart();
////
//        myBroadcastReceiver = new MyBroadcastReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.submitSuccessful");  // 给注册好的广播添加一个频道，用来接收发送给这个频道的sendBroadcast
//        getActivity().registerReceiver(myBroadcastReceiver,filter);// 注册一个广播而已,还没有发送广播呢!

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                            // 这个container就是父容器，one_fragment中的Relatvice就不是根节点，因为它有父容器，所以它的布局参数的宽度和高度都会生效
        View view = inflater.inflate(R.layout.one_fragment, container, false);
        Button button = (Button) view.findViewById(R.id.btn_one_submit);
        et_get = ((EditText) view.findViewById(R.id.et_get));
        et_pain = ((EditText) view.findViewById(R.id.et_pain));
        et_comm = ((EditText) view.findViewById(R.id.et_comm));
        login_username = getActivity().getIntent().getStringExtra("login_username");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"121",Toast.LENGTH_SHORT).show();
                if (et_get.getText().toString().length()>0&&et_pain.getText().toString().length()>0&&et_comm.getText().toString().length()>0 && login_username.length()>0) {
                    Accout accout = new Accout();
                    accout.setGet(et_get.getText().toString());
                    accout.setPain(et_pain.getText().toString());
                    accout.setComm(et_comm.getText().toString());
//                    accout.setDate(sdf.format(Calendar.getInstance().getTime()));
                    accout.setLoginUsername(login_username);
                    Log.d("google-my:", "one_submit: "+et_comm.getText().toString());
                    Log.d("google-my:", "one_submit: "+et_pain.getText().toString());
                    Log.d("google-my:", "one_submit: "+et_get.getText().toString());
                    Log.d("google-my:", "onClick: ");
                    accout.save(new SaveListener<String>() {
                        private MyBroadcastReceiver myBroadcastReceiver;

                        @Override
                        public void done(String s, BmobException e) {
                            if(e == null) // 代表保存成功
                            {
                                Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_SHORT).show();

                                // 发送广播
                                Intent intent = new Intent("android.submitSuccessful");
                                intent.putExtra("is","1");
                                getActivity().sendBroadcast(intent);

                            }else
                            {

                            }
                        }
                    });
                }else
                {
                    Toast.makeText(getActivity(),"信息不允许出现空白",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() { // 如果在第一个Fragment里面就解除注册广播的话，应用退出去，歌曲就不能播放了啊
        super.onDestroyView();

//        getActivity().unregisterReceiver(myBroadcastReceiver);
    }
}
