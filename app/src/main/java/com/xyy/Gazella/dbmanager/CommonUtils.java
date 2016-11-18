package com.xyy.Gazella.dbmanager;

import android.content.Context;
import android.util.Log;

import com.partner.dao.PartnerDao;
import com.partner.entity.Partner;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 完成对某一张表的具体操作, ORM 操作的是对象
 */
public class CommonUtils {
    private DaoManager mDaoManager;

    public CommonUtils(Context context) {
        mDaoManager = DaoManager.getInstance();
        mDaoManager.init(context);
    }

    /**
     * 完成对数据库表的插入操作-->并且会检测数据库是否存在,不存在自己创建,
     */
    public boolean insertPartner(Partner Partner) {
        boolean flag = false;
        flag = mDaoManager.getSession().insert(Partner) != -1;//不等于-1是true 否则是false
        Log.i("MainActivity", "insertPartner: " + flag);
        return flag;
    }

    /**
     * 同时插入多条记录
     */
    public boolean insertMultPartner(final List<Partner> Partners) {
        boolean flag = false;
        try {
            mDaoManager.getSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Partner s : Partners) {
                        mDaoManager.getSession().insertOrReplace(s);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("MainActivity", "insertMultPartner: " + flag);
        return false;
    }

    /**
     * 修改指定记录
     */
    public boolean uoDatePartner(Partner Partner) {
        boolean flag = false;
        try {
            mDaoManager.getSession().update(Partner);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("MainActivity", "uoDatePartner: " + flag);
        return flag;
    }

    /**
     * 删除指定记录
     */
    public boolean deletePartner(Partner Partner) {
        boolean flag = false;
        try {
            mDaoManager.getSession().delete(Partner);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("MainActivity", "deletePartner: " + flag);
        return flag;
    }

    /**
     * 删除所有的记录
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            mDaoManager.getSession().deleteAll(Partner.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("aaa", "deleteAll: " + flag);
        return flag;
    }

    /**
     * 查询 某一个表 的 所有记录
     */
    public List<Partner> listAll() {
        return mDaoManager.getSession().loadAll(Partner.class);
    }

    /**
     * 按照主键查询某一个 表 中 的单行记录
     */
    public Partner listOnePartner(long key) {
        return mDaoManager.getSession().load(Partner.class, key);
    }

    /**
     * 按照sql语句进行查询
     */
    public void queryBySql() {
        List<Partner> list = mDaoManager.getSession().queryRaw(Partner.class, "where name like ? and _id<=?", new String[]{"%jo%", "4"});
        for (Partner s : list) {
            Log.i("MainActivity", s.getId() + "");
        }
    }

    /**
     * 使用查询构建器进行查询
     */
    public List<Partner> queryByBuilder(String Type,String Data) {
        //使用查询构建器
        QueryBuilder<Partner> queryBuilder = mDaoManager.getSession().queryBuilder(Partner.class);
        //这些条件是 逻辑与
        queryBuilder.where(PartnerDao.Properties.Type.like(Type));
        List<Partner> list = queryBuilder.where(PartnerDao.Properties.Date.le(Data)).list();
        return  list;
    }

}
