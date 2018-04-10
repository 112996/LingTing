package yejiangxia.lingting.Music;

/**
 * Created by asus-pc on 2017/8/21.
 */

public class Song {
    //歌曲名字
    private String mSongName;

    //歌曲文件名字
    private String mSongFileName;

    //歌曲的长度
    private int mNameLength;

    //将字符串转化为字符数组
    public char[] getNameCharacters(){
        return mSongName.toCharArray();
    }

    public String getSongName() {
        return mSongName;
    }

    public void setSongName(String songName) {
        this.mSongName =  songName;

        this.mNameLength = songName.length();
    }

    public String getSongFileName() {
        return mSongFileName;
    }

    public void setSongFileName(String songFileName) {
        this.mSongFileName = songFileName;
    }

    public int getNameLength() {
        return mNameLength;
    }
}
