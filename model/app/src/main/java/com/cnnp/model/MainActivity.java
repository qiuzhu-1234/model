package com.cnnp.model;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.cnnp.model.activity.AutoKeyBoardActivity;
import com.cnnp.model.activity.CalendarUseActivity;
import com.cnnp.model.common.BaseActivity;
import com.cnnp.model.db.StudentInfo;
import com.cnnp.model.db.manager.StudentInfoManager;
import com.cnnp.model.fragment.PinkPigFragment;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity {

    private TextView click_1;
    private TextView click_2;
    private TextView click_3;
    private TextView click_4;
    private TextView click_5;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        click_1 = findViewById(R.id.click_1);
        click_2 = findViewById(R.id.click_2);
        click_3 = findViewById(R.id.click_3);
        click_4 = findViewById(R.id.click_4);
        click_5 = findViewById(R.id.click_5);
    }

    @Override
    protected void initDate() {

        click_1.setOnClickListener(new View.OnClickListener() {//验证fragment功能使用 （闪退）
            @Override
            public void onClick(View view) {
                navigateTo(PinkPigFragment.class);
            }
        });

        click_2.setOnClickListener(new View.OnClickListener() {//验证本地数据库使用
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

        click_3.setOnClickListener(new View.OnClickListener() {//验证键盘自适应问题
            @Override
            public void onClick(View view) {
                navigateTo(AutoKeyBoardActivity.class);
            }
        });


        click_4.setOnClickListener(new View.OnClickListener() {//验证时间转换问题
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
              //获取当前时间
                //Date类型
                Date time1 = new Date();
                System.out.println("Date类型："+time1); //Tue Apr 05 10:47:11 GMT 2022
                //Instant类型  （秒）
                Instant time2 = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(8));//2022-04-05T18:47:11.547Z
                System.out.println("Instant类型："+time2);
                //时间戳类型
                Calendar calendar = Calendar.getInstance();//1649155631549
                long time3 = calendar.getTimeInMillis();
                System.out.println("时间戳类型:"+time3);

            }
        });

        click_5.setOnClickListener(new View.OnClickListener() {//验证键盘自适应问题
            @Override
            public void onClick(View view) {
                navigateTo(CalendarUseActivity.class);
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