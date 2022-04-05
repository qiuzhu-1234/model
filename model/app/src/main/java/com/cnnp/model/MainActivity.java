package com.cnnp.model;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cnnp.model.activity.AutoKeyBoardActivity;
import com.cnnp.model.common.BaseActivity;
import com.cnnp.model.db.StudentInfo;
import com.cnnp.model.db.manager.StudentInfoManager;
import com.cnnp.model.fragment.PinkPigFragment;

import java.util.List;

public class MainActivity extends BaseActivity {

    private ImageView im_pink_pig;
    private ImageView im_frog;
    private ImageView im_panda;
    private ImageView im_mouse;
    private ImageView im_elephant;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        im_pink_pig = findViewById(R.id.im_pink_pig);
        im_frog = findViewById(R.id.im_frog);
        im_panda = findViewById(R.id.im_panda);
        im_mouse = findViewById(R.id.im_mouse);

    }

    @Override
    protected void initDate() {

        im_pink_pig.setOnClickListener(new View.OnClickListener() {//验证fragment功能使用 （闪退）
            @Override
            public void onClick(View view) {
                navigateTo(PinkPigFragment.class);
            }
        });

        im_frog.setOnClickListener(new View.OnClickListener() {//验证本地数据库使用
            @Override
            public void onClick(View view) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setName("张三");
                studentInfo.setChniese(30);
                studentInfo.setMath(40);
                studentInfo.setEnglish(50);
                studentInfo.setTotal("120");
                new StudentInfoManager(MainActivity.this).insert(studentInfo);
                Log.v("###studentInfo",studentInfo.toString());

                List<StudentInfo> list = new StudentInfoManager(MainActivity.this).getAll();
                Log.v("list:",list.toString());
//                getAppInfo();
            }
        });

        im_panda.setOnClickListener(new View.OnClickListener() {//验证键盘自适应问题
            @Override
            public void onClick(View view) {
                navigateTo(AutoKeyBoardActivity.class);
            }
        });


        im_mouse.setOnClickListener(new View.OnClickListener() {//验证时间转换问题
            @Override
            public void onClick(View view) {

            }
        });




    }
//
//    private void getAppInfo()
//    {
//        // 获取packageManager的实例
//        PackageManager packageManager = getPackageManager();
//        // getPackageName()是你当前类的包名，0代表是获取版本信息
//        try
//        {
//            PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0 );
//            Log.v("#######",packInfo.getClass().getPackage().getSpecificationVersion());
//        }
//        catch (PackageManager.NameNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//    }


}