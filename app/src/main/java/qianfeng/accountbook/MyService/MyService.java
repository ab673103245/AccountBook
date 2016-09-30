package qianfeng.accountbook.MyService;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2016/9/27 0027.
 */
public class MyService extends Service {//Action:  ---->  android.myservice
                                // MyMusicBroadcast的频道: android.mymusicBroadcast

    public MediaPlayer mediaPlayer;
    private boolean isPlaying;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();// 把这个Service对象传出去
    }

    public class MyBinder extends Binder{

        public MyService getInstance()
        {
            Log.d("google-my:", "getInstance: " + MyService.this.hashCode());
            return MyService.this;
        }

    }

    public void initMusicPlayer()
    {
        mediaPlayer = new MediaPlayer();

        Log.d("google-my:", "initMusicPlayer: 新new出来的这个mediaPlayer的hashCode: " + mediaPlayer.hashCode());
        try {
            mediaPlayer.setDataSource(this, Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"m1.mp3")));
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play()
    {
        if(mediaPlayer == null)
        {
            initMusicPlayer();
        }

//        if(!isPlaying) {
            mediaPlayer.start();
//            isPlaying = true;
//        }

    }

    public void pause()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public void stop()
    {
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }

    public void resetMediaPlayer(File file)
    {
        if(mediaPlayer != null) // 只要之前有过实例，都会被清空
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(this,Uri.fromFile(file));
                mediaPlayer.prepare();
                mediaPlayer.start(); // 重置完之后，肯定是要播放的啊

                isPlaying = true; // 现在这个isPlaying是无什么所谓的啊,因为还没有用到它

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onUnbind(Intent intent) {
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Log.d("google-my:", "onUnbind: Service +++++++++++++++++++");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d("google-my:", "onDestroy: Service +++++++---------------------->");
        super.onDestroy();
    }
}
