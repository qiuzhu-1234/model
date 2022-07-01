package com.cnnp.model.db;

import android.content.Context;

import com.cnnp.model.common.LogUtil;

import java.util.List;

public class ModelBaseDao<T> {
    public static final boolean DUBUG = true;
    public ModelDaoManager manager;
    public DaoSession daoSession;
    public ModelBaseDao(Context context){
        manager = ModelDaoManager.getInstance();
        manager.init(context);
        daoSession = manager.getDaoSession();
        manager.setDebug(DUBUG);
    }
    //数据库插入操作
    /**
     * 插入单个对象
     */
    public boolean insertObject(T object){
        boolean flag = false;
        try{
            flag = manager.getDaoSession().insert(object) !=-1 ? true:false;
        }catch (Exception e){
            LogUtil.e(e.toString());
        }
        return flag;
    }

    /**
     * 插入多个对象，并开启新的线程
     */
    public boolean insertMultObject(final List<T> objects){
        boolean flag = false;
        if (null == objects || objects.isEmpty()){
            return false;
        }
        try{
            manager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (T object : objects){
                        manager.getDaoSession().insertOrReplace(object);
                    }
                }
            });
            flag = true;
        }catch (Exception e){
            LogUtil.e(e.toString());
            flag = false;
        }finally {
            manager.closeDataBase();
        }
        return flag;


    }

    //数据库更新操作
    /**
     * 以对象形式进行数据修改
     * 其中必须瑶知道对象的主键ID
     */
    public void updateObject(T object){
        if(null == object){
            return ;
        }
        try{
            manager.getDaoSession().update(object);
        }catch (Exception e){
            LogUtil.e(e.toString());
        }
    }

    /**
     * 单个对象更新数据（string int long）
     *      * 先查询 再更新
     *      *
     *      * Student load = studentDao.load("0");
     *      *
     *      * load.setHobby("足球");
     *      *
     *      * studentDao.update(load);
     */

    /**
     * 批量更新数据
     */
    public void updateMultObject(final List<T> objects,Class clss){
        if(null == objects || objects.isEmpty()){
            return ;
        }
        try{
            daoSession.getDao(clss).updateInTx(new Runnable() {
                @Override
                public void run() {
                    for(T object : objects){
                        daoSession.update(object);
                    }
                }
            });
        }catch (Exception e){
            LogUtil.e(e.toString());
        }

    }

    //数据库删除操作
    /**
     * 删除某个数据表
     */
    public boolean deleteAll(Class clss){
        boolean flag = false;
        try{
            manager.getDaoSession().deleteAll(clss);
            flag = true;

        }catch (Exception e){
            LogUtil.e(e.toString());
            flag = false;
        }
        return  flag;
    }

    /**
     * 删除某个对象
     */
    public void deleteObject(T object){
        try{
            daoSession.delete(object);
        }catch (Exception e){
            LogUtil.e(e.toString());
        }
    }

    /**
     * 一步批量删除数据
     */
    public boolean deleteMultObject(final List<T> objects,Class clss){
        boolean flag = false;
        if(null == objects || objects.isEmpty()){
            return false;
        }
        try{
            daoSession.getDao(clss).deleteInTx(new Runnable() {
                @Override
                public void run() {
                    for(T object : objects){
                        daoSession.delete(object);
                    }
                }
            });
            flag = true;
        }catch (Exception e){
            LogUtil.e(e.toString());
            flag = false;
        }
        return flag;
    }

    //数据库查询操作
    /**
     * 获得某个表名
     */
    public String getTablename(Class object){
        return daoSession.getDao(object).getTablename();
    }

    /**
     * 查询某个ID的对象是否存在
     */
    public boolean isExitObject(long id, Class object){
       return false;
    }

    /**
     * 根据主键ID来查询
     */
    public T QueryById(long id, Class object){
        return (T) daoSession.getDao(object).loadByRowId(id);
    }

    /**
     * 查询某条件下的对象
     */
    public List<T> QueryObject(Class object, String where, String...params){
        Object obj = null;
        List<T>  objects = null;
        try{
            obj = daoSession.getDao(object);
            if(null == obj){
                return null;
            }
            objects = daoSession.getDao(object).queryRaw(where,params);
        }catch (Exception e){
            LogUtil.e(e.toString());
        }

        return objects;
    }

    /**
     * 查询所有对象
     */
    public List<T> QueryAll(Class object){
        List<T> objects = null;
        try{
            objects = (List<T>)  daoSession.getDao(object).loadAll();
        }catch (Exception e){
            LogUtil.e(e.toString());
        }

        return objects;
    }

    //关闭数据库
    /**
     * 关闭数据库一般在Odestory中使用
     */

    public void CloseDataBase(){
        manager.closeDataBase();
    }
}
