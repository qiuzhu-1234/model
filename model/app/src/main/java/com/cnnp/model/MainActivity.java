package com.cnnp.model;


import android.view.View;
import com.cnnp.model.activity.AutoKeyBoardActivity;
import com.cnnp.model.activity.CalendarUseActivity;
import com.cnnp.model.activity.LocalDataPoolActivity;
import com.cnnp.model.activity.TimeTypeChangeActivity;
import com.cnnp.model.bluetooh.BlueToothMainActivity;
import com.cnnp.model.common.BaseActivity;
import com.cnnp.model.fragment.PinkPigFragment;


public class MainActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        findViewById(R.id.click_1).setOnClickListener(MainActivity.this);
        findViewById(R.id.click_2).setOnClickListener(MainActivity.this);
        findViewById(R.id.click_3).setOnClickListener(MainActivity.this);
        findViewById(R.id.click_4).setOnClickListener(MainActivity.this);
        findViewById(R.id.click_5).setOnClickListener(MainActivity.this);
        findViewById(R.id.click_6).setOnClickListener(MainActivity.this);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.click_1:  //fragment
                navigateTo(PinkPigFragment.class);
                break;
            case R.id.click_2:  //本地数据库 greendao
                navigateTo(LocalDataPoolActivity.class);
                break;
            case R.id.click_3:  //键盘自适应
                navigateTo(AutoKeyBoardActivity.class);
                break;
            case R.id.click_4:  //时间类型转换
                navigateTo(TimeTypeChangeActivity.class);
                break;
            case R.id.click_5: //时间控件
                navigateTo(CalendarUseActivity.class);
                break;
            case R.id.click_6:   //蓝牙连接
                navigateTo(BlueToothMainActivity.class);
                break;
        }
    }

    @Override
    protected void initDate() {

    }


}