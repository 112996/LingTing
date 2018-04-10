package yejiangxia.lingting.Music;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by asus-pc on 2017/8/21.
 */

public class MyPlayer {
    //在播放音乐的时候可以调用强制重置，也可以在设置数据源的时候放入初始化

    //做音效的时候最好用soundPool，等项目完成后再来实现

    //索引
    public static final int INDEX_TONE_ENTER = 0;
    public static final int INDEX_TONE_CANCEL = 1;
    public static final int INDEX_TONE_COIN = 2;
    public static final int INDEX_TONE_NEXT = 3;
    public static final int INDEX_TONE_WRONG = 4;

    //音效文件名
    private final static String[] SONG_NAMES ={
            "enter.mp3","cancel.mp3","coin.mp3","next.mp3","wrong.mp3"
    } ;

    //音效数组
    private static MediaPlayer[] mToneMediaPlayer =
            new MediaPlayer[SONG_NAMES.length];

    /**
     * 播放音效
     * @param context
     * @param index
     */
    public static void playTone(Context context, int index){
        AssetManager assetManager = context.getAssets();

        if(mToneMediaPlayer[index] == null){
            mToneMediaPlayer[index] = new MediaPlayer();

            try {
                AssetFileDescriptor fileDescriptor = assetManager.openFd(SONG_NAMES[index]);

                mToneMediaPlayer[index].setDataSource(fileDescriptor.getFileDescriptor(),
                        fileDescriptor.getStartOffset(),fileDescriptor.getLength());

                mToneMediaPlayer[index].prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mToneMediaPlayer[index].start();
//        //强制重置
//        mToneMediaPlayer[index].reset();
    };

    //歌曲播放
    private static MediaPlayer mMusicMediaPlayer;

    /**
     * 播放歌曲
     *
     * @param context
     * @param fileName
     */
    public static void playSong(Context context,String fileName){
        if(mMusicMediaPlayer == null){
            mMusicMediaPlayer = new MediaPlayer();
        }

        //强制重置
        mMusicMediaPlayer.reset();

        //加载声音文件
        AssetManager assetManager = context.getAssets();
        try {
            AssetFileDescriptor fileDescriptor = assetManager.openFd(fileName);
            //获取数据源
            mMusicMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                    fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());

            mMusicMediaPlayer.prepare();
            mMusicMediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static boolean isPlaying(){
        if (mMusicMediaPlayer!=null){
            mMusicMediaPlayer.isPlaying();
        }
        return true;
    }
    public static boolean setLooping(){
        if (mMusicMediaPlayer!=null){
            mMusicMediaPlayer.setLooping(true);
            mMusicMediaPlayer.start();
        }
        return true;
    }
    public static void stopTheSong(Context context){
        if(mMusicMediaPlayer != null){
            mMusicMediaPlayer.stop();
        }
    }
}
