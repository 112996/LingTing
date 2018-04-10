package yejiangxia.lingting.Mood_User;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import yejiangxia.lingting.R;

/**
 * Created by asus-pc on 2017/7/14.
 */

public class Mood_SelfDailog extends Dialog {
    private EditText Tel_num;
    private EditText ver_code;
    private Button get_ver_code;
    private ImageButton mood_btn_in;

    private onGet_ver_codeOnclickListener get_ver_codeOnclickListener;
    private onMood_btn_inOnclickListener mood_btn_inOnclickListener;
    private String Get_ver_codeStr,Mood_btn_inStr;


    public Mood_SelfDailog(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }

    public void setGet_ver_codeOnclickListener(String str, onGet_ver_codeOnclickListener onGet_ver_codeOnclickListener){
        if (str != null){

            Get_ver_codeStr=str;

        }
        this.get_ver_codeOnclickListener=onGet_ver_codeOnclickListener;

    }
    public void setMood_btn_inOnclickListener(String str, onMood_btn_inOnclickListener onMood_btn_inOnclickListener){
        if (str != null){

            Mood_btn_inStr=str;

        }
        this.mood_btn_inOnclickListener=onMood_btn_inOnclickListener;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_dialog);
        setCanceledOnTouchOutside(true);
        initView();
        initEvent();

    }

    public void initView(){

        Tel_num= (EditText) findViewById(R.id.Tel_num);
        ver_code= (EditText) findViewById(R.id.ver_code);
        get_ver_code= (Button) findViewById(R.id.get_ver_code);
        mood_btn_in= (ImageButton) findViewById(R.id.mood_btn_in);
    }
    public void initEvent(){

        get_ver_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(get_ver_codeOnclickListener!= null){
                    get_ver_codeOnclickListener.onGet_ver_codeClick();
                }
            }
        });
        mood_btn_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mood_btn_inOnclickListener!=null){
                    mood_btn_inOnclickListener.onMood_btn_inClick();
                }
            }
        });

    }

    public interface onGet_ver_codeOnclickListener{
        public void onGet_ver_codeClick();
    }
    public interface onMood_btn_inOnclickListener{
        public void onMood_btn_inClick();
    }

}
