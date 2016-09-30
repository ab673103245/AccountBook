package qianfeng.accountbook.MyBroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import qianfeng.accountbook.MyService.MyService;
import qianfeng.accountbook.R;

/**
 * Created by Administrator on 2016/9/25 0025.
 */
public class MyBroadcastReceiver extends BroadcastReceiver { // 广播里面也是可以使用Handler的，Handler无处不在，Handler在四大组件中无处不在。

    private MyService myService;

    private File sdCardRootDirctory;
    private List<File> mp3songList;
    private List<String> mp3;
    private File currentSong;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 当我接收到按钮的点击事件传过来的handler消息时
            switch (msg.what) {
                case 0:
                    alertDialog.dismiss();
                    break;
                case 1:  // 1 是查找好音乐文件了！
                {
                    // 查找好音乐文件之后，就监听当前这首歌有没有播放完成，如果播放完成了，那就切换下一首歌

                    // 得到当前歌曲在List<File>中的索引
                    currentSongPosition = mp3songList.indexOf(currentSong);

                    myService.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // 音乐播放完成时自动调用
                            // 播放下一首歌,在没有收到ListView点击事件的时候，自动播放下一首歌
                            // private List<File> mp3songList;
                            int location = (currentSongPosition + 1) % mp3songList.size(); // 列表循环播放
                            myService.resetMediaPlayer(mp3songList.get(location));
                            currentSong = mp3songList.get(location);

                            sendEmptyMessage(1);

                        }
                    });
                }

                break;
            }
        }
    };
    private AlertDialog alertDialog;
    private String filename;
    private int currentSongPosition;  // 得到当前歌曲在List<File>中的索引

    @Override
    public void onReceive(Context context, Intent intent) { // 果然使用intent区分广播事件的处理！！！

        if (intent.getStringExtra("is").equals("1")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.submit_alertdialog, null); // 加载了一个布局之后，布局里面的所有控件，都要在这个布局里面找
            Button btn_sure = (Button) view.findViewById(R.id.sub_sure);
            builder.setView(view);
            builder.setCancelable(true);
            alertDialog = builder.create();
            alertDialog.show();

            btn_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHandler.sendEmptyMessage(0);
                }
            });
        } else if (intent.getStringExtra("is").equals("2")) {
            filename = intent.getStringExtra("filename");
            Intent service = new Intent("android.myservice");
            ServiceConnection conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    // 绑定成功时调用
                    myService = ((MyService.MyBinder) service).getInstance();


                    myService.resetMediaPlayer(new File(filename)); // 每当我点击一个ListView的item的时候，只要Lv的点击事件触发了，这个myService中的MediaPlayer对象都会被重置，原本的那个的播放资源会被清空

                    currentSong = new File(filename);


                    // 我在广播这里更新UI
                    int duration = myService.mediaPlayer.getDuration();

                    // 开一个子线程，查找音乐文件
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mp3songList = new ArrayList<>();
                            mp3 = new ArrayList<>();

                            sdCardRootDirctory = Environment.getExternalStorageDirectory();
                            findMp3(sdCardRootDirctory);

                            if (mp3songList.size() > 0 && mp3songList != null) {
                                for (int i = 0; i < mp3songList.size(); i++) {
                                    Log.d("google-my:", "run: " + mp3songList.get(i).getAbsolutePath());
                                    mp3.add(mp3songList.get(i).getName());
                                }
                                mHandler.sendEmptyMessage(1); // 通知主线程，查找好了歌曲了！1是查找好音乐文件了！
                            }

                        }
                    }).start();



                }

                private void findMp3(File sdCardRootDirctory) {
                    if (sdCardRootDirctory.isDirectory()) {
                        File[] files = sdCardRootDirctory.listFiles();
                        for (File f : files) {
                            Log.d("google-my:", "findMp3: 我在哪个文件夹里面？" + f.getAbsolutePath());
                            if (f.isDirectory()) {
                                Log.d("google-my:", "findMp3: FourFragment我是目录这行有执行吗？++我是递归文件的目录");
                                findMp3(f);
                            } else {
                                if (f.getName().toLowerCase().endsWith(".mp3")) {
                                    Log.d("google-my:", "findMp3: FourFragment我会进入里面这个else吗，我是递归文件的里面的else");
                                    mp3songList.add(f);
                                }
                            }
                        }
                    } else {
                        if (sdCardRootDirctory.getName().toLowerCase().endsWith(".mp3")) {
                            Log.d("google-my:", "findMp3: FourFragment 外面这个else有执行吗？我是递归文件外面的else");
                            mp3songList.add(sdCardRootDirctory);
                        }
                    }
                }


                @Override
                public void onServiceDisconnected(ComponentName name) {
                    // 绑定断开时调用

                }
            };

            // 开启一个绑定式服务
            context.bindService(service, conn, Context.BIND_AUTO_CREATE);

        } else if (intent.getStringExtra("is").equals("3")) // 播放按钮
        {
            // 把从Service中获取过来的MediaPlayer做成一个全局通用的变量

            myService.mediaPlayer.start();
        } else if (intent.getStringExtra("is").equals("4")) // 暂停按钮
        {

            myService.mediaPlayer.pause();
        } else if (intent.getStringExtra("is").equals("5")) // 停止按钮
        {

            myService.mediaPlayer.stop(); // 要在Service里面判断播放有没有多次按下！暂停有没有多次按下等细节的操作
        } else if(intent.getStringExtra("is").equals("6"))
        {
            int progress = intent.getIntExtra("progress", 5000);
            myService.mediaPlayer.seekTo(progress);
        }

        // 一个Activity被多次调用onCreate方法，首先它会先执行ondestroy(),然后才会重新执行onCreate(),所以，绑定的服务也会解绑
        // 注意我这里是动态注册的广播啊！里面绑定的服务也是动态注册广播成功执行时，才会绑定服务啊什么的


    }
}
