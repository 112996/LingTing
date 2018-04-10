package yejiangxia.lingting.Mood_User;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import yejiangxia.lingting.R;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText Tel_num;
    private  EditText ver_code;
    private Button get_ver_code;
    private Button mood_btn_yes;
    private Button mood_btn_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        Bmob.initialize(Login.this,"725a4d1f9a2f01728c4888b843c2ef46");
        BmobSMS.initialize(Login.this,"725a4d1f9a2f01728c4888b843c2ef46");
        initView();
        initEvent();



    }

    private void initEvent() {
        get_ver_code.setOnClickListener(this);
        mood_btn_yes.setOnClickListener(this);
        mood_btn_in.setOnClickListener(this);

    }

    private void initView() {
        get_ver_code= (Button) findViewById(R.id.get_ver_code);
        mood_btn_yes= (Button) findViewById(R.id.mood_btn_yes);
        mood_btn_in= (Button) findViewById(R.id.mood_btn_in);
        Tel_num= (EditText) findViewById(R.id.Tel_num);
        ver_code= (EditText) findViewById(R.id.ver_code);
    }
    @Override
    public void onClick(View view) {
        Log.e("MESSAGE:", "1");
        String userName = Tel_num.getText().toString();
        String passWord = ver_code.getText().toString();
        switch (view.getId()) {

            case R.id.get_ver_code:
                Log.e("MESSAGE", "2");
                if (userName.length() != 11) {
                    View toastRoot = getLayoutInflater().inflate(R.layout.my_toast,null);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setView(toastRoot);
                    TextView tv = (TextView) toastRoot.findViewById(R.id.toast);
                    tv.setText("请输入11位有效手机号~");
                    toast.show();
                } else {

                    Log.e("MESSAGE", "3");
                    BmobSMS.requestSMSCode(this, userName, "聆听儿A", new RequestSMSCodeListener() {
                        @Override
                        public void done(Integer integer, BmobException e) {
                            if (e == null) {
                                get_ver_code.setClickable(false);
                                //get_ver_code.setBackgroundColor(Color.GRAY);
                                View toastRoot = getLayoutInflater().inflate(R.layout.my_toast,null);
                                Toast toast = new Toast(getApplicationContext());
                                toast.setView(toastRoot);
                                TextView tv = (TextView) toastRoot.findViewById(R.id.toast);
                                tv.setText("验证码发送成功，请尽快使用~");
                                toast.show();
                                new CountDownTimer(60000, 1000) {

                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                        //get_ver_code.setBackgroundResource(R.color.colorBg);
                                        get_ver_code.setText(millisUntilFinished / 1000 + "秒");
                                    }

                                    @Override
                                    public void onFinish() {
                                        get_ver_code.setClickable(true);
                                        get_ver_code.setBackgroundResource(R.color.colorBg);
                                        get_ver_code.setText("重新发送");
                                    }
                                }.start();
                                Log.e("MESSAGE", "4");
                            } else {

                                View toastRoot = getLayoutInflater().inflate(R.layout.my_toast,null);
                                Toast toast = new Toast(getApplicationContext());
                                toast.setView(toastRoot);
                                TextView tv = (TextView) toastRoot.findViewById(R.id.toast);
                                tv.setText("验证码发送失败，请检查网络连接~");
                                toast.show();
                            }
                        }
                    });
                }
                break;
            case R.id.mood_btn_yes:
                Log.e("MESSAGE:", "5");
                if (userName.length() == 0 || passWord.length() == 0 || userName.length() != 11) {
                    Log.e("MESSAGE:", "6");

                    View toastRoot = getLayoutInflater().inflate(R.layout.my_toast,null);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setView(toastRoot);
                    TextView tv = (TextView) toastRoot.findViewById(R.id.toast);
                    tv.setText("手机号或验证码输入不合法~");
                    toast.show();
                } else {
                    BmobSMS.verifySmsCode(this, userName, passWord, new VerifySMSCodeListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.e("MESSAGE:", "7");
                                View toastRoot = getLayoutInflater().inflate(R.layout.my_toast,null);
                                Toast toast = new Toast(getApplicationContext());
                                toast.setView(toastRoot);
                                TextView tv = (TextView) toastRoot.findViewById(R.id.toast);
                                tv.setText("登录成功~");
                                toast.show();
                                /*final Intent m = new Intent(Login.this, Picture_and_Name.class);
                                startActivity(m);
                                finish();
*/


                            } else {
                                Log.e("MESSAGE:", "8");
                                View toastRoot = getLayoutInflater().inflate(R.layout.my_toast,null);
                                Toast toast = new Toast(getApplicationContext());
                                toast.setView(toastRoot);
                                TextView tv = (TextView) toastRoot.findViewById(R.id.toast);
                                tv.setText("验证码错误~");
                                toast.show();
                            }
                        }

                    });
                }
                break;
            case R.id.mood_btn_in:

                String tel_num = Tel_num.getText().toString();
                if(TextUtils.isEmpty(tel_num)&&TextUtils.isEmpty(passWord)){
                    View toastRoot = getLayoutInflater().inflate(R.layout.my_toast,null);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setView(toastRoot);
                    TextView tv = (TextView) toastRoot.findViewById(R.id.toast);
                    tv.setText("请输入手机号！");
                    toast.show();
                }else{
                    BmobQuery<User> query = new BmobQuery<User>();
                    query.addWhereEqualTo("UserTel", tel_num);
                    query.setLimit(500);
                    query.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> object, cn.bmob.v3.exception.BmobException e) {
                            if (e == null) {
                                System.out.println("\"查询成功：共\"+object.size()+\"条数据。\"");
                                if (object.size()==0) {
                                    final Intent m = new Intent(Login.this, Picture_and_Name.class);
                                    startActivity(m);
                                    finish();
                                }else{
                                    final Intent m = new Intent(Login.this, Mood_main.class);
                                    startActivity(m);
                                    finish();
                                }
                            } else {
                                Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                        }
                    });
                }
        }
    }
}
