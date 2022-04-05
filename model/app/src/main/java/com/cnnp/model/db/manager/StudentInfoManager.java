package com.cnnp.model.db.manager;

import android.content.Context;

import com.cnnp.model.db.ModelBaseDao;
import com.cnnp.model.db.StudentInfo;
import com.cnnp.model.db.StudentInfo;

import java.util.List;

public class StudentInfoManager extends ModelBaseDao<StudentInfo> {
    public StudentInfoManager(Context context) {
        super(context);
    }

    public void insert (StudentInfo studentInfo){
        daoSession.getStudentInfoDao().insertOrReplace(studentInfo);
    }

    public List<StudentInfo> getAll() {
        return  daoSession.getStudentInfoDao().loadAll();
    }
}
