package com.cnnp.model.activity;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.cnnp.model.R;
import com.cnnp.model.common.BaseActivity;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeTypeChangeActivity extends BaseActivity {

    @Override
    protected int initLayout() {
        return R.layout.activity_time_type_change;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initDate() {
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
}