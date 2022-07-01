package com.cnnp.model.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cnnp.model.R;
import com.cnnp.model.common.BaseActivity;
import com.cnnp.model.common.LogUtil;
import com.cnnp.model.common.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.InputStream;
import java.text.BreakIterator;

import cz.msebera.android.httpclient.Header;

/**
 * 上传图片
 */

public class UploadImageActivity extends BaseActivity implements View.OnClickListener{

    private Bitmap tt;
    private String photoPath;
    private BreakIterator path;
    private ImageView photo;
    private Bitmap bitmap;
    private static final int PERMISSIONS_REQUEST_PHONE_STATE = 0;

    @Override
    protected int initLayout() {
        return R.layout.activity_upload_image;
    }

    @Override
    protected void initListener() {
        findViewById(R.id.img_add).setOnClickListener(UploadImageActivity.this);
    }

    @Override
    protected void initView() {
        //动态授权
        //initDynamicAutho();
    }

    @Override
    protected void initDate() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_add:
                LogUtil.d("####" + "55555555");
                //使用Intent触发选择Action
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                //打开系统提供的图片选择界面
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                //传参以在返回本界面时触发加载图片的功能
                startActivityForResult(intent, 0x1);
                break;
        }
    }

    //在选择图片后返回本界面调用此方法
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {

                ContentResolver resolver = getContentResolver();
                try {
                    // 获取图片URI
                    Uri uri = data.getData();
                    // 将URI转换为路径：
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    //  这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    // 最后根据索引值获取图片路径
                    photoPath = cursor.getString(column_index);
                    LogUtil.d("photoPath##" + photoPath);

                   // convert("/storage/emulated/0/Pictures/IMG_20220627_080430.jpg", "png", "/storage/emulated/0/Pictures/IMG_20220627_080430.png");

                    //bitmap = getBitmapP(data);

                    bitmap = BitmapFactory.decodeFile(photoPath);
//
//                    Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
//                            null, null, null);
//
//                    if (cursor != null) {
//                        while (cursor.moveToNext()) {
//                            //获取唯一的id
//                            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID));
//                            //通过id构造Uri
//                            Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
//                            //解析uri
//                            decodeUriForBitmap(uri);
//                        }
//                    }

//                    //相册选择图片
//                    try (InputStream inputStream = this.getApplicationContext().getContentResolver().openInputStream(uri)) {
//                       //  bitmap = BitmapFactory.decodeStream(inputStream);//得到bitmap
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }


                    //bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

                   // bitmap = BitmapFactory.decodeFile(photoPath);
//                    try {
//                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(photoPath));
//                        bitmap = BitmapFactory.decodeStream(bis);
//                        bis.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    // 压缩成800*480  暂时不用
                    //bitmap = BitmapUtils.decodeSampledBitmapFromFd(photoPath, 240, 400);
                    // 设置imageview显示图片
                    photo.setImageBitmap(bitmap);
                    //设置textview显示路径
                    path.setText(photoPath);

                    upload("/storage/emulated/0/Movies");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private Bitmap getBitmapP(Intent data) {
        try {
            ImageDecoder.Source sourceMap = ImageDecoder.createSource(getContentResolver(), data.getData());
            return ImageDecoder.decodeBitmap(sourceMap).copy(Bitmap.Config.ARGB_8888, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param source 源图片路径
     * @param formatName 将要转换的图片格式
     * @param result 目标图片路径
     */
    public static void convert(String source, String formatName, String result) {
        try {
            File f = new File(source);
            f.canRead();
//            //Android 不支持BufferedImage类
//            BufferedImage  src = ImageIO.read(f);
//            ImageIO.write(src, formatName, new File(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeUriForBitmap(Uri uri) {
        if (uri == null)
            return;
        try {
            //构造输入流
            InputStream inputStream = getContentResolver().openInputStream(uri);
            //解析Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            if (bitmap != null)
                Log.d("test", "bitmap width-width:" + bitmap.getWidth() + "-" + bitmap.getHeight());
        } catch (Exception e) {
            Log.d("test", e.getLocalizedMessage());
        }
    }

    public void upload(String url) {
        // 将bitmap转为string，并使用BASE64加密
        String photo = Utils.BitmapToString(bitmap);
        // 获取到图片的名字
        String name = photoPath.substring(photoPath.lastIndexOf("/")).substring(1);
        // new一个请求参数
        RequestParams params = new RequestParams();
        // 将图片和名字添加到参数中
        params.put("photo", photo);
        params.put("name", name);
        AsyncHttpClient client = new AsyncHttpClient();
        // 调用AsyncHttpClient的post方法
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
                Toast.makeText(getApplicationContext(), "上传失败!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                Toast.makeText(getApplicationContext(), "上传成功!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 动态授权
     */
    @SuppressLint("MissingPermission")
    private void initDynamicAutho() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请权限(返回数组类型，多个权限一次性申请)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_PHONE_STATE);
        }else {
            String aaa = "duduud";
            LogUtil.d("相机权限已经授权了："+ aaa);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限(返回数组类型，多个权限一次性申请)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_PHONE_STATE);
        }else {
            String aaa = "duduud";
            LogUtil.d("读文件已经授权了："+ aaa);
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限(返回数组类型，多个权限一次性申请)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_PHONE_STATE);
        }else {
            String aaa = "duduud";
            LogUtil.d("写文件已经授权了："+ aaa);
        }

//        if (ContextCompat.checkSelfPermission(this,Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            //申请权限(返回数组类型，多个权限一次性申请)
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_PHONE_STATE);
//        }else {
//            String aaa = "duduud";
//            LogUtil.d("访问所有文件已经授权了："+ aaa);
//        }

    }
}