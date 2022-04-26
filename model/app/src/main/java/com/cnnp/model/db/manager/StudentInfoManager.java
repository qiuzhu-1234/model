package com.cnnp.model.db.manager;

import android.content.Context;

import com.cnnp.model.db.ModelBaseDao;
import com.cnnp.model.db.StudentInfo;
import com.cnnp.model.db.StudentInfoDao;

import org.greenrobot.greendao.query.WhereCondition;

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

    public void update (StudentInfo studentInfo) {
        daoSession.getStudentInfoDao().update(studentInfo);
    }

    public List<StudentInfo> getByString(String str){
        return daoSession.getStudentInfoDao().queryBuilder()
                .where(StudentInfoDao.Properties.Total.eq(str))
                .orderAsc(StudentInfoDao.Properties.Id)
                .list();
    }



    /** 先查询在更新
     * Student load = studentDao.load("0");
     *
     * load.setHobby("足球");
     *
     * studentDao.update(load);
     */

    public void updateByStringRequest(String total){

    }

}
