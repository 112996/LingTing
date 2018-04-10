package yejiangxia.lingting.Mood_User;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import yejiangxia.lingting.R;

public class SelfDialog extends Dialog {


    private Button yes;
    private Button no;

    private onYesOnclickListener yesOnclickListener;
    private onNoOnclickListener noOnclickListener;
    private String YesStr,NoStr;

    public SelfDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }
    public SelfDialog(){
        super(null);
    }

    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener){
        if (str != null){

            YesStr=str;

        }
        this.yesOnclickListener=onYesOnclickListener;

    }
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener){
        if (str != null){

            NoStr=str;

        }
        this.noOnclickListener=onNoOnclickListener;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_dialog);
        setCanceledOnTouchOutside(true);
        initView();
        initEvent();

    }

    public void initEvent(){

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(yesOnclickListener!= null){
                    yesOnclickListener.onYesClick();
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noOnclickListener!=null){
                    noOnclickListener.onNoClick();
                }
            }
        });

    }

    public void initView(){

       yes= (Button) findViewById(R.id.yes);
        no= (Button) findViewById(R.id.no);
    }

    public interface onYesOnclickListener{
         void onYesClick();
    }
    public interface onNoOnclickListener{
         void onNoClick();
    }



}
