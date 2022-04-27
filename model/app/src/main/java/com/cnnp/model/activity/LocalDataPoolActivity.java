package com.cnnp.model.activity;

import com.cnnp.model.MainActivity;
import com.cnnp.model.R;
import com.cnnp.model.common.BaseActivity;
import com.cnnp.model.common.LogUtil;
import com.cnnp.model.db.StudentInfo;
import com.cnnp.model.db.manager.StudentInfoManager;

import java.util.List;

public class LocalDataPoolActivity extends BaseActivity {


    @Override
    protected int initLayout() {
        return R.layout.activity_local_data_pool;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDate() {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setName("王二");
        studentInfo.setChniese(30);
        studentInfo.setMath(40);
        studentInfo.setEnglish(50);
        studentInfo.setTotal("250");
        new StudentInfoManager(this).insert(studentInfo);
        LogUtil.d("###studentInfo" + studentInfo.toString());

        //更新操作 （依据某个字段）
        List<StudentInfo> studentInfos = new StudentInfoManager(this).getByString("250");
        for(StudentInfo info : studentInfos){
            info.setName("李四");
            new StudentInfoManager(this).update(info);
        }
    }
}