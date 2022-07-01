package com.cnnp.model.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cnnp.model.R;
import com.cnnp.model.common.BaseActivity;
import com.cnnp.model.common.LogUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 缓存图片
 */

public class DownloadFileActivity extends BaseActivity implements View.OnClickListener{

    private TextView et_file_path;

    private String path = "https://pan.baidu.com/s/1lrME6md0sP1kDU1MVfMSsg?pwd=b8yl";
    //private String path = "file:///H:/spring-boot/demo-mysql/files/some.docx";

    private static final int PERMISSIONS_REQUEST_PHONE_STATE = 0;

    @Override
    protected int initLayout() {
        return R.layout.activity_download_file;
    }

    @Override
    protected void initListener() {
        findViewById(R.id.download_file).setOnClickListener(DownloadFileActivity.this);
    }

    @Override
    protected void initView() {
        et_file_path = findViewById(R.id.et_file_path);

        //动态授权 （安卓11及以上版本需动态授权）
        //initDynamicAutho();
    }

    @Override
    protected void initDate() {

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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.download_file:
                LogUtil.d("####" + "6666");
                //H:\spring-boot\demo-mysql\files\some.docx  (报错)

                // file://H:/spring-boot/demo-mysql/files/some.docx （正确写法）
                //String path = et_file_path.getText().toString();
                try {
                    downLoad( path, DownloadFileActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     *
     * @param path
     * @param context
     * @throws Exception
     */
    public static void downLoad(String path,Context context)throws Exception
    {
        LogUtil.d("path##" + path);


        new Thread(new Runnable(){
            @Override
            public void run() {

                URL url = null;
                try {
                    url = new URL(path);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                InputStream is = null;
                try {
                    is = url.openStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //截取最后的文件名
                String end = path.substring(path.lastIndexOf("."));
                //打开手机对应的输出流,输出到文件中
                OutputStream os = null;
                try {
                    os = context.openFileOutput("Cache_"+System.currentTimeMillis()+".pdf", Context.MODE_PRIVATE);
                    //os = context.openFileOutput("Cache_"+System.currentTimeMillis()+".docx", Context.MODE_PRIVATE);
                    //os = context.openFileOutput("Cache_"+System.currentTimeMillis()+end, Context.MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                byte[] buffer = new byte[1024];
                int len = 0;
                //从输入六中读取数据,读到缓冲区中
                while(true)
                {
                    try {
                        if (!((len = is.read(buffer)) > 0)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        os.write(buffer,0,len);

                        //打开文件
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //关闭输入输出流
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
}