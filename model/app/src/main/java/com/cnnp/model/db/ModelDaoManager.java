package com.cnnp.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

public class ModelDaoManager {
    private static final String TAG = ModelDaoManager.class.getSimpleName();
    private static final String DB_NAME ="pink_pig.db";//数据库名称
    private volatile static ModelDaoManager mDaoManager;//多线程访问
    private static com.cnnp.model.db.DaoMaster.DevOpenHelper mHelper;
    private static com.cnnp.model.db.DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;
    private static SQLiteDatabase db;
    private Context context;


    /**
     * 使用单例模式获得操作数据库的对象
     *
     */
    public static ModelDaoManager getInstance() {
        ModelDaoManager instance = null;
        if (mDaoManager == null) {
            synchronized (ModelDaoManager.class){
                if(instance == null){
                    instance = new ModelDaoManager();
                    mDaoManager = instance;
                }
            }
        }
        return  mDaoManager;
    }

    /**
     * 初始化Context对象
     */
    public void init(Context context){ this.context = context;}

    /**
     * 判断数据库是否存在，不存在则创建
     */
    //SQLiteDatabase: /data/user/0/com.cn.model/databases/pink_pig.db
    public com.cnnp.model.db.DaoMaster getDaoMaster(){
        if(null == mDaoMaster){
            mHelper = new com.cnnp.model.db.DaoMaster.DevOpenHelper(context, DB_NAME, null);
            mDaoMaster = new com.cnnp.model.db.DaoMaster(mHelper.getWritableDatabase());
        }
        return mDaoMaster;
    }

    /**
     * 完成对数据库的增删查找
     */
    public DaoSession getDaoSession(){
        if(null == mDaoSession){
            if(null == mDaoMaster){
                mDaoMaster = getDaoMaster();
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return  mDaoSession;
    }

    /**
     * 设置debg模式开启或关闭，默认关闭
     * @param flag
     */
    public void setDebug (boolean flag){
        QueryBuilder.LOG_SQL = flag;
        QueryBuilder.LOG_VALUES = flag;
    }

    /**
     * 关闭数据路
     */
    public void closeDataBase(){
        closeHelper();
        closeDaoSession();
    }

    public void closeDaoSession(){
        if(null != mDaoSession){
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    public void closeHelper(){
        if(mHelper != null){
            mHelper.close();
            mHelper = null;
        }
    }

}
