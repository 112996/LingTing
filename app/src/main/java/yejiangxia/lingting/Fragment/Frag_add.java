package yejiangxia.lingting.Fragment;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import yejiangxia.lingting.Main.Count;
import yejiangxia.lingting.Main.MainActivity;
import yejiangxia.lingting.Mood_User.Add;
import yejiangxia.lingting.Mood_User.User;
import yejiangxia.lingting.R;

public class Frag_add extends Fragment {
    private EditText add_text;
    private ImageView add_picture;
    private Button add_publish;
    private String text;
    private static  final String iconPath = Environment.getExternalStorageDirectory()+"/Image";
    private String path;
    private Integer i = 0;
    private final long SPLASH_LENGTH = 3000;
    Handler handler=new Handler();

   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_frag_add, container,false);


       add_text=(EditText)view.findViewById(R.id.add_text);
       add_picture=(ImageView) view.findViewById(R.id.add_picture);
       add_publish=(Button) view.findViewById(R.id.add_publish);


       add_picture.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               takePhoto(view);
           }
               /*//dialog = new Dialog(Frag_add.this,R.style.ActionSheetDialogStyle);
               dialog= new Dialog(Mood_main.class, R.style.ActionSheetDialogStyle);
               inflate = LayoutInflater.from(Mood_main.this).inflate(R.layout.picture_dialog_layout, null);
               //View popupView = View.inflate(MainActivity.this,R.layout.dialog_layout,null);
               choosePhoto = (Button) inflate .findViewById(R.id.choosePhoto);
               takePhoto = (Button) inflate .findViewById(R.id.takePhoto);
               cancel = (Button) inflate .findViewById(R.id.btn_cancel);
               choosePhoto.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       selectPic(view);

                   }
               });*/
               /*takePhoto.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       takePhoto(view);
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
               //lp.width=-1;
               //lp.width = (int) getResources().getDisplayMetrics().widthPixels;
               int width = getResources().getDisplayMetrics().widthPixels;
               lp.width= width;
               //lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
               dialogWindow.setAttributes(lp);
               dialog.show();
           }*/
      });

       add_publish.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               text=add_text.getText().toString();
               //User user= BmobUser.getCurrentUser(User.class);
               Add add= new Add();
               add.setText(text);
               //add.setAuther(user);
               add.setNum(i);
               add.save(new SaveListener<String>() {
                   @Override
                   public void done(String s, BmobException e) {
                       if (e==null){
                           Log.i("tag", "添加数据成功", e);
                       }else {
                           Log.i("tag", "添加数据失败", e);
                       }
                   }
               });
             /* final BmobFile bmobfile = new BmobFile(new File(iconPath));
               bmobfile.upload(new UploadFileListener() {
                   @Override
                   public void done(BmobException e) {
                       if (e==null){
                           System.out.println("图片上传成功");
                           User user= BmobUser.getCurrentUser(User.class);
                           Add add= new Add();
                           add.setText(text);
                           add.setAuther(user);
                           add.setNum(i);
                           add.setPicture(bmobfile);
                           add.save(new SaveListener<String>() {
                               @Override
                               public void done(String s, BmobException e) {
                                   if (e==null){
                                       //Toast.makeText(Frag_add.this,"添加成功",Toast.LENGTH_SHORT).show();
                                       //System.out.println("添加数据成功");
                                       Log.i("tag", "添加数据成功", e);

                                   }else {
                                       //Toast.makeText(Picture_and_Name.this,"添加失败",Toast.LENGTH_SHORT).show();
                                       //System.out.println("添加数据失败");
                                       Log.i("tag", "添加数据失败", e);
                                   }
                               }
                           });
                            *//*MyTask task=new MyTask();
                            task.execute(bmobfile.getFileUrl());*//*
                       }else{
                           //Toast.makeText(Picture_and_Name.this, "文件上传失败", Toast.LENGTH_SHORT).show();
                           //System.out.println("文件上传失败");
                           Log.i("tag", "文件上传失败", e);
                       }

                   }
               });*/
                            exchange();
           }
       });



       return view;
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

        }
    }
    public void exchange(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                add_text.setText("");
                add_picture.setImageResource(R.mipmap.add_pic);
            }
        },SPLASH_LENGTH);
    }
    /**
     * 获取选择的图片
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (requestCode==1){
            Log.i("-----返回成功-----","摄像头启动");
            if (data==null){
                Log.i("-----返回数据空-----","用户没有进行拍照");
                return;
            }else {
                Bundle result=data.getExtras();
                if (result!=null) {
                    Bitmap bm = result.getParcelable("data");//拿到数据存入bm
                    Uri uri = BitMap(bm);
                    add_picture.setImageBitmap(bm);
      /*  if (resultCode == -1) {
            Uri uri = data.getData();
            path = getImagePath(uri, null);
            ContentResolver cr = this.getContentResolver();
            try {
                Log.e("qwe", path.toString());
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                *//* 将Bitmap设定到ImageView *//*
                add_picture.setImageBitmap(bitmap);
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

    public ContentResolver getContentResolver() {
        throw new RuntimeException("Stub!");

    }
}*/
                }}}}
    private Uri BitMap(Bitmap bitmap){
                    File tmpDir = new File(Environment.getExternalStorageDirectory() + "/Bmob");    //保存地址及命名
                    if (!tmpDir.exists()) {
                        tmpDir.mkdir(); //生成目录进行保存
                    }
                    File img = new File(tmpDir.getAbsolutePath() + "upload.png");
                    try {
                        FileOutputStream fos = new FileOutputStream(img);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 85, fos);  //参:压缩的格式，图片质量85，输出流
                        fos.flush();
                        fos.close();
                        return Uri.fromFile(img);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }}