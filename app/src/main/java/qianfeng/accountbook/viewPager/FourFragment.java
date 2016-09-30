package qianfeng.accountbook.viewPager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import qianfeng.accountbook.R;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class FourFragment extends Fragment {

    private List<File> mp3songList;
    private List<String> mp3;
    private String filename;
    private int k = 1;
    private int j = 1;
    private boolean isPlaying;

    private int currentSongDuration;
    private File currentSong;
    private int currentSongPosition;
    private MediaPlayer mediaPlayer;
    private boolean isSliding;
    private boolean isFirst = true;
    private boolean isFirstClick = true;



    private SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");

    private int currentProgress;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //  在这里更新UI, 先查找到ListView吧

            switch (msg.what) {
                case 0: {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mp3);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //只要是ListView的item被点击了，才会发送广播
                            Toast.makeText(getActivity(), mp3songList.get(position).getAbsolutePath(), Toast.LENGTH_SHORT).show();



                            Intent intent = new Intent("android.submitSuccessful"); // 发送一个广播的频道
                            intent.putExtra("is", "2");
                            filename = mp3songList.get(position).getAbsolutePath();
                            intent.putExtra("filename", filename);
                            getActivity().sendBroadcast(intent);

                            //其实我所有UI都可以在这里更新哦！
                            tv_songname.setText(mp3songList.get(position).getName());

                            mediaPlayer = new MediaPlayer();

                            try {
                                mediaPlayer.setDataSource(getActivity(), Uri.fromFile(new File(filename)));
                                mediaPlayer.prepare();

                                currentSongDuration = mediaPlayer.getDuration();
                                currentSong = new File(filename);
                                currentSongPosition = mp3songList.indexOf(currentSong);

                                int duration = mediaPlayer.getDuration();
                                String totalTime = FourFragment.this.dateFormat.format(new Date(duration));
                                tv_four_totalTime.setText(totalTime);
                                seekBar.setMax(duration);// 给个seekBar也设置一下吧
                                isPlaying = true;
                                isSliding = false;

                                if (isFirstClick) {
                                    sendEmptyMessage(4);// 通知currentTime更新时间,在case4里面延迟一秒更新UI
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            // 如果歌曲播放完了，我要自动播放下一首，这个我该怎么实现？我怎么直到当前的歌曲是播放完了？
                            // 你要隔1秒发送一个消息，判断currentProgress是否等于全局的getDuration，如果等于的话，就从集合里面传另一个文件对象，
                            // 准备歌曲信息，在这个过程中更新seekBar，textView等控件

                            if (isFirstClick) {
                                sendEmptyMessage(5);
                            }

                            k = 1;
                            isFirstClick = false;

                            // 我可能是在点击了之后，就对seekBar的进度条进行了拖动什么的。在这里处理歌曲的快进和快退
                            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    if (fromUser) // 如果是来自用户的拖动
                                    {
                                        // 发送一条广播，在广播里面处理歌曲的实际的快进
                                        Intent intent = new Intent("android.submitSuccessful"); // 发送一个广播的频道
                                        intent.putExtra("is", "6");
                                        intent.putExtra("progress", progress);
                                        getActivity().sendBroadcast(intent);

                                        String currentTime = FourFragment.this.dateFormat.format(new Date(progress));
                                        tv_four_currentTime.setText(currentTime);

                                        k = progress / 1000;

                                        j = progress / 1000;


                                    }
                                }

                                // 开始拖动进度条的时候
                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) {


                                }

                                // 进度条结束的时候调用
                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {

                                }
                            });

                        }
                    });
                }
                break;

                case 1: {  // 播放
                    // 发送一条广播，让歌曲播放，注意里面携带intent参数，让广播可以获取到
                    Intent intent = new Intent("android.submitSuccessful");
                    intent.putExtra("is", "3");
                    getActivity().sendBroadcast(intent);
                    // 顺带在这里更新UI啦！！
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    try {

                        mediaPlayer.setDataSource(getActivity(), Uri.fromFile(new File(filename)));
//
//                        if(filename == null)
//                        {
//                            mediaPlayer.setDataSource(getActivity(),Uri.fromFile(new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)),"m1.mp3")));
//                        }
//

                        mediaPlayer.prepare();
                        int duration = mediaPlayer.getDuration();
                        String totalTime = FourFragment.this.dateFormat.format(new Date(duration));


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    isPlaying = true;

                }
                break;

                case 2: // 暂停
                    // 在1-3的case中，我只做一件事，那就是发广播，利用广播里获取到的Service对象的MediaPlayer实例，来操作里面歌曲的播放暂停等操作。
                {
                    Intent intent = new Intent("android.submitSuccessful");
                    intent.putExtra("is", "4");
                    getActivity().sendBroadcast(intent);
                    isPlaying = false;
                }
                break;

                case 3: // 停止
                {
                    Intent intent = new Intent("android.submitSuccessful");
                    intent.putExtra("is", "5");
                    getActivity().sendBroadcast(intent);

                    // 我按了停止按钮之后，要干什么？
                    // 清空本MediaPlayer的所有信息以及UI显示的所有信息，并准备了第一首歌用于准备播放

                    String format = dateFormat.format(new Date(0));
                    String format2 = dateFormat.format(new Date(0));
                    tv_four_currentTime.setText(format);
                    tv_four_totalTime.setText(format2);
                    tv_songname.setText("当前没有歌曲");
                    seekBar.setProgress(0);
                    k = 1;


                }

                break;

                case 4: {
                    // 延迟一秒更新currentTime
                    sendEmptyMessageDelayed(4, 1000);

                    if (isPlaying) {
                        if (!isSliding) {
                            seekBar.setProgress(k * 1000);

                            int progress = seekBar.getProgress();

                            currentProgress = seekBar.getProgress();

                            String format = dateFormat.format(new Date(progress));

                            tv_four_currentTime.setText(format);

                            ++k;
                        }
                    }

                }

                break;

                case 5: {
                    // 你要隔1秒发送一个消息，判断currentProgress是否等于全局的getDuration，如果等于的话，就从集合里面传另一个文件对象，
                    // 准备歌曲信息，在这个过程中更新seekBar，textView等控件(这里是更新Ui的地方)
                    sendEmptyMessageDelayed(5, 1000);
                    if (isPlaying) {
                        if (currentProgress == currentSongDuration) {

                            /*
                             int location = (currentSongPosition + 1) % mp3songList.size(); // 列表循环播放
                            myService.resetMediaPlayer(mp3songList.get(location));
                             */
                            currentSong = mp3songList.get((currentSongPosition + 1) % mp3songList.size());
                            currentSongPosition += 1;

                            try {
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                mediaPlayer = null;
                                mediaPlayer = new MediaPlayer();
//                                    isSliding = false;
                                j = 1;

                                isSliding = true;

                                mediaPlayer.setDataSource(getActivity(), Uri.fromFile(currentSong));
                                mediaPlayer.prepare();

                                int duration = mediaPlayer.getDuration();
                                String totalTime = FourFragment.this.dateFormat.format(new Date(duration));
                                currentSongDuration = duration;

                                tv_four_totalTime.setText(totalTime);

                                seekBar.setProgress(0);
                                seekBar.setMax(duration);// 给个seekBar也设置一下吧
                                tv_songname.setText(currentSong.getName());
                                tv_four_currentTime.setText("00:00");

                                isPlaying = true;

                                if (isFirst) {
                                    sendEmptyMessage(6);// 通知currentTime更新时间,在case4里面延迟一秒更新UI
                                }
                                isFirst = false;


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
                break;

                case 6:  // 6是专门处理由于播放完毕切换下一首歌而引起的更新Ui的问题 4是专门用来处理点击ListView之后的更新Ui问题
                {
                    sendEmptyMessageDelayed(6, 1000);

                    if (isPlaying) {
                        seekBar.setProgress(j * 1000);

                        int progress = seekBar.getProgress();

                        currentProgress = seekBar.getProgress();

                        String format = dateFormat.format(new Date(progress));

                        tv_four_currentTime.setText(format);

                        ++j;
                    }
                }
                break;


            }

        }
    };
    private File sdCardRootDirctory;
    private ListView listView;
    private SeekBar seekBar;
    private TextView tv_four_totalTime;
    private TextView tv_four_currentTime;
    private TextView tv_songname;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mp3songList = new ArrayList<>();
        mp3 = new ArrayList<>();

        // 遍历本机的所有文件夹，记得是子线程中执行


        new Thread(new Runnable() {
            @Override
            public void run() {
                // 线程睡眠时，才需要判断当前Activity有没有被销毁,这时才决定要不要开启线程

                sdCardRootDirctory = Environment.getExternalStorageDirectory();
                findMp3(sdCardRootDirctory);

                if (mp3songList.size() > 0 && mp3songList != null) {
                    for (int i = 0; i < mp3songList.size(); i++) {
                        Log.d("google-my:", "run: " + mp3songList.get(i).getAbsolutePath());
                        mp3.add(mp3songList.get(i).getName());
                    }
                    mHandler.sendEmptyMessage(0); // 通知主线程，查找好了歌曲了！
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.four_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.listview_four);

//         遍历本机中的所有文件，找出音乐文件，并存储到集合中,在onCreate()方法中完成吧

        // 现在是要监听rg_four的点击事件啦！
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg_four);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb1_four: // 播放
                        // 我在这里发送Handler消息
                        mHandler.sendEmptyMessage(1);

                        break;

                    case R.id.rb2_four: // 暂停
                        mHandler.sendEmptyMessage(2);

                        break;

                    case R.id.rb3_four: // 停止
                        mHandler.sendEmptyMessage(3);

                        break;
                }
            }
        });


        // 寻找SeekBar , 统一用handler来更新UI吧
        seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        tv_four_totalTime = ((TextView) view.findViewById(R.id.tv_four_totalTime));
        tv_four_currentTime = ((TextView) view.findViewById(R.id.tv_four_currentTime));
        tv_songname = ((TextView) view.findViewById(R.id.tv_four_songName));


        return view;
    }


}
