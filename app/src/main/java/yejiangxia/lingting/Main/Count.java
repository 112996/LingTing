package yejiangxia.lingting.Main;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import yejiangxia.lingting.BrokenLine.BrokenLineCusVisit;
import yejiangxia.lingting.BrokenLine.BrokenLineCusVisitView;
import yejiangxia.lingting.BrokenLine.BrokenLineZanshi;
import yejiangxia.lingting.Mood_User.Mood_main;
import yejiangxia.lingting.Music.Const;
import yejiangxia.lingting.Mood_User.Login;
import yejiangxia.lingting.Mood_User.Mood_SelfDailog;
import yejiangxia.lingting.Music.MyPlayer;
import yejiangxia.lingting.R;
import yejiangxia.lingting.Mood_User.SelfDialog;
import yejiangxia.lingting.Music.Song;

public class Count extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView appname;
    private ImageButton imageButton;
    private ImageView imageView;
    private ImageButton record;
    private long prelongTim = 0;
    private boolean isFirstClick = true;
    private boolean doo = true;
    private TimeCount time;
    private TextView timer1;
    private TextView timer2;
    private TextView degree;
    private Button stop;
    private SQLiteDatabase dbWrite;
    private SelfDialog selfDialog;
    private  int d;
    private ImageButton music;
    private Song myCurrentSong;
    private int isMusicClick = 1;
    private boolean isrun=true;
    private boolean isFirstIn=true;

    /*新加的侧拉栏*/
    private DrawerLayout myDrawerLayout;
    private NavigationView myNaviView;

    /*折线图*/
    private List<BrokenLineCusVisit> mdata = new ArrayList<>();
    private BrokenLineCusVisitView brokenline;
    private SQLiteDatabase dbRead;
    private List<BrokenLineZanshi> zzdata = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_count);

        myCurrentSong = loadStageSongInfo(0);
        if (savedInstanceState!=null){
            Toast.makeText(Count.this,"SavedInstance not null",Toast.LENGTH_SHORT).show();
        }

        initView();

        /*折线图*/
        DB db = new DB(this);
        dbRead = db.getReadableDatabase();
        Cursor c = dbRead.query("user", null, null, null, null, null, null);

        BrokenLineCusVisit b = new BrokenLineCusVisit();
        BrokenLineZanshi d = new BrokenLineZanshi();
        String[] now = new String[300];

        String[] had = new String[300];
        int[] end = new int[300];
        while (c.moveToNext()) {
            for (int i = 0; i < c.getCount(); i++) {
                c.moveToPosition(i);
                String date1 = c.getString(c.getColumnIndex("date"));
                Integer count = c.getInt(c.getColumnIndex("count"));
                d.setDate(date1);
                d.setData(count);
                zzdata.add(new BrokenLineZanshi(d));
                Log.d("MDATA_TAG", "succed");
            }
        }
        try {
            int j = 0;
            int cnt = 0;
            int other=0;
            int sum = 0;
            now[j] = zzdata.get(j).getDate();
            System.out.println(now[j]);
            for (j=0; j < zzdata.size(); ) {
                now[j] = zzdata.get(j).getDate();
                for (int m =j ; m < zzdata.size(); m++) {
                    if (now[j].equals(zzdata.get(m).getDate()) == true) {
                        if (cnt != 3) {
                            cnt += 1;
                            sum = sum + zzdata.get(m).getData();

                        } else {
                            cnt = 3;
                        }
                        System.out.println(sum);
                        other++;
                    }
                }
                b.setData(sum*4);
                b.setDate(zzdata.get(j).getDate());
                System.out.println(sum);
                mdata.add(new BrokenLineCusVisit(b));
                sum=0;
                cnt = 0;
                j=j+other;
                other=0;
            }
        } catch (IndexOutOfBoundsException e) {

        } catch (NullPointerException e) {
        }

        brokenline.setMdata(mdata);


    }

    private void startActivitySafely(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "ActivityNotFoundExceptionnull",
                    Toast.LENGTH_SHORT).show();
        } catch (SecurityException e) {
            Toast.makeText(this, "SecurityExceptionnull", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void initView() {
        appname= (TextView) findViewById(R.id.appname);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageView = (ImageView) findViewById(R.id.imageView);
        degree = (TextView) findViewById(R.id.degree);
        timer1 = (TextView) findViewById(R.id.timer1);
        timer2 = (TextView) findViewById(R.id.timer2);
        time = new TimeCount(3600000, 1000);
        record = (ImageButton) findViewById(R.id.record);
        music = (ImageButton) findViewById(R.id.music);
        stop= (Button) findViewById(R.id.stop);



        /*新加的侧拉栏*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toobar);
        myDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        myNaviView = (NavigationView) findViewById(R.id.navigationview);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, myDrawerLayout, toolbar, R.string.open, R.string.close);
        myDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        myNaviView.setNavigationItemSelectedListener(this);
        myNaviView.getMenu().findItem(R.id.mood).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(false);
                if (isFirstIn){
                Intent i = new Intent(Count.this, Login.class);
                startActivity(i);
                    isFirstIn=false;
                }else{
                    Intent i = new Intent(Count.this, Mood_main.class);
                    startActivity(i);

                }
                
                return false;
            }
        });
        myNaviView.getMenu().findItem(R.id.about).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                item.setChecked(false);
                View toastRoot = getLayoutInflater().inflate(R.layout.my_toast,null);
                Toast toast = new Toast(getApplicationContext());
                toast.setView(toastRoot);
                TextView tv = (TextView) toastRoot.findViewById(R.id.toast);
                tv.setText("此应用还在努力研发中~");
                toast.show();
                return false;
            }
        });


        /*新增的音乐自定义弹窗*/
        music = (ImageButton) findViewById(R.id.music);
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMusicClick == 1) {
                    music.setBackgroundResource(R.mipmap.music_press);
                    MyPlayer.playSong(Count.this, myCurrentSong.getSongFileName());
                    LoopMusic();
                    isMusicClick = 1 * (-1);
                } else {
                    music.setBackgroundResource(R.mipmap.music);
                    MyPlayer.stopTheSong(Count.this);
                    isMusicClick = 1;

                }
               
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selfDialog = new SelfDialog(Count.this);
                selfDialog.setYesOnclickListener("确定",
                        new SelfDialog.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                appname.setText("聆听儿");
                                time.cancel();
                                timer2.setText(" ");
                                timer1.setText(" ");
                                degree.setText("开始计时");
                                isFirstClick=true;
                                doo=true;
                                imageButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Animation loadAnimation = AnimationUtils.loadAnimation(Count.this, R.anim.rotate);
                                        imageView.startAnimation(loadAnimation);

                                        isClick();
                                        isDo();
                                    }
                                });


                                selfDialog.dismiss();
                            }
                        });

                selfDialog.setNoOnclickListener("取消",
                        new SelfDialog.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {

                               selfDialog.dismiss();
                            }
                        });
                selfDialog.show();

            }
        });

        /*折线图*/
        brokenline = (BrokenLineCusVisitView) findViewById(R.id.brokenline);



        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation loadAnimation = AnimationUtils.loadAnimation(Count.this, R.anim.rotate);
                imageView.startAnimation(loadAnimation);

                isClick();
                isDo();
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent m = new Intent(Count.this, Record.class);
                startActivity(m);
            }
        });

    }
    private void LoopMusic() {
        if (isrun==true && MyPlayer.isPlaying()){
            MyPlayer.setLooping();
        }
    }
    private Song loadStageSongInfo(int stageIndex){
        Song song = new Song();
        String[] stage = Const.SONG_INFO[stageIndex];
        song.setSongFileName(stage[Const.INDEX_FILE_NAME]);
        song.setSongName(stage[Const.INDEX_SONG_NAME]);
        return song;
    }

    public void isClick(){
        if (isFirstClick){
            degree.setText("1");
            isFirstClick=false;
            time.start();
            appname.setText("胎动计数中...");


        }


    }

    public void isDo(){
        if (doo){

            if (prelongTim == 0){
                prelongTim = (new Date()).getTime();
            }else {
                long curTim = (new Date()).getTime();
                long Tim = curTim - prelongTim;
                if(Tim >= 1000 * 300){
                    int num = Integer.valueOf(degree.getText().toString());
                    num++;
                    degree.setText(Integer.toString(num));
                    prelongTim = curTim;
                }else {
                    View toastRoot = getLayoutInflater().inflate(R.layout.my_toast,null);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setView(toastRoot);
                    TextView tv = (TextView) toastRoot.findViewById(R.id.toast);
                    tv.setText("五分钟之内的连续活动只能算一次有效胎动~");
                    toast.show();
                }

            }
        }

    }




    class  TimeCount extends CountDownTimer{

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long minutes = (millisUntilFinished % (1000 * 60 * 60))/(1000 * 60);
            String minute=""+minutes;
            timer1.setText(minute+":");
            long seconds=(millisUntilFinished % (1000 * 60))/1000;
            String second = ""+seconds;
            timer2.setText(second+" s");
        }

        @Override
        public void onFinish() {
            doo=false;
            appname.setText("聆听儿");
            timer1.setText("");
            timer2.setText("");
            DB db = new DB(Count.this);
            dbWrite = db.getWritableDatabase();
            ContentValues cv = new ContentValues();
            Date currentDate = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyy/MM/dd");
            String date = formatter.format(currentDate);
            cv.put("date",date);
            Date currenTime = new Date();
            SimpleDateFormat formatter1 = new SimpleDateFormat("HH:mm");
            String time = formatter1.format(currenTime);
            cv.put("time",time);
            d = Integer.valueOf(degree.getText().toString());
            cv.put("count",d);
            dbWrite.insert("user",null,cv);
            dbWrite.close();
            degree.setText("开始计时");
        }
    }

/*新加的侧拉栏*/
    public void onBackPressed(){
        if (myDrawerLayout.isDrawerOpen(GravityCompat.START)){
            myDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public  boolean onNavigationItemSelected(MenuItem item){
        myDrawerLayout.closeDrawer(GravityCompat.START);
        return  true;
    }

    class DB extends SQLiteOpenHelper {


        public DB(Context context) {
            super(context, "db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE user(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "date TEXT DEFAULT\"\"," +
                    ""+" time TEXT DEFAULT\"\","+
                    "count INTEGER DEFAULT\"\")");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
