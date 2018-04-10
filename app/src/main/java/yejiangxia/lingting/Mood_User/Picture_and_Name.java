package yejiangxia.lingting.Mood_User;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import yejiangxia.lingting.R;

public class Picture_and_Name extends AppCompatActivity {

    private CircleImageView head_portrait;
    private EditText Name;
    private EditText Expect_time;
    private EditText Tel_num;
    private Button submit,choosePhoto,cancel;
    private Dialog dialog;
    private View inflate;
    private String name,expect_time;
    private String  tel_num;
    private static  final String iconPath = Environment.getExternalStorageDirectory()+"/Image";
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_picture_and__name);

        initView();
        initEvent();
    }
    public void initView(){
        head_portrait= (CircleImageView) findViewById(R.id.head_portrait);
        Name= (EditText) findViewById(R.id.Name);
        submit= (Button) findViewById(R.id.submit);
        Tel_num= (EditText) findViewById(R.id.Tel_num);
        Expect_time= (EditText) findViewById(R.id.Expect_time);
    }

    public void initEvent(){
        head_portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(Picture_and_Name.this,R.style.ActionSheetDialogStyle);
                inflate = LayoutInflater.from(Picture_and_Name.this).inflate(R.layout.picture_dialog_layout, null);
                choosePhoto = (Button) inflate .findViewById(R.id.choosePhoto);
                cancel = (Button) inflate .findViewById(R.id.btn_cancel);
                choosePhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectPic(view);

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });
                dialog.setContentView(inflate);
                Window dialogWindow = dialog.getWindow();
                dialogWindow.setGravity( Gravity.BOTTOM);
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.y = 20;
                int width = getResources().getDisplayMetrics().widthPixels;
                lp.width= width;
                dialogWindow.setAttributes(lp);
                dialog.show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=Name.getText().toString();
                tel_num=Tel_num.getText().toString();
                expect_time=Expect_time.getText().toString();
                if(TextUtils.isEmpty(name)&&TextUtils.isEmpty(tel_num)&&TextUtils.isEmpty(expect_time)){
                    View toastRoot = getLayoutInflater().inflate(R.layout.my_toast,null);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setView(toastRoot);
                    TextView tv = (TextView) toastRoot.findViewById(R.id.toast);
                    tv.setText("请输入完整信息！");
                    toast.show();
                }else{
                    final BmobFile bmobfile = new BmobFile(new File(path));
                    bmobfile.upload(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                System.out.println("图片上传成功");
                                User user= new User();
                                user.setUserName(name);
                                user.setUserTel(tel_num);
                                user.setBownDate(expect_time);
                                user.setIcon(bmobfile);
                                user.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e==null){
                                            System.out.println("添加数据成功");
                                        }else {
                                            System.out.println("添加数据失败");
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(Picture_and_Name.this, "文件上传失败", Toast.LENGTH_SHORT).show();
                                System.out.println("文件上传失败");
                            }

                        }
                    });
                    final Intent m = new Intent(Picture_and_Name.this, Mood_main.class);
                    startActivity(m);
                    finish();

                }

            }
        });

    }
    /**
     * 打开系统相册，并选择图片
     */
    public void selectPic(View view){
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        startActivityForResult(intent,0);
    }
    /**
     * 选择拍照的图片
     */
    public void takePhoto(View view){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 1);
    }
    /**
     * 给拍的照片命名
     */
    public String createPhotoName(){
        //以系统的当前时间给图片命名
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = format.format(date)+".jpg";
        return fileName;
    }
    /**
     * 把拍的照片保存到SD卡
     */
    public void saveToSDCard(Bitmap bitmap) {
        //先要判断SD卡是否存在并且挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(iconPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(createPhotoName());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);//把图片数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if(outputStream!=null){
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            Toast.makeText(this,"SD卡不存在",Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 获取选择的图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            path = getImagePath(uri, null);
            ContentResolver cr = this.getContentResolver();
            try {
                Log.e("qwe", path.toString());
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                /* 将Bitmap设定到ImageView */
                head_portrait.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("qwe", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();

        }
        return path;
    }
}


