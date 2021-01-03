package com.example.df_daily.Helper;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;

        import com.example.df_daily.bean.DaoMaster;
        import com.example.df_daily.bean.DaoSession;
        import com.example.df_daily.bean.DailyInfo;
        import com.example.df_daily.bean.DailyInfoDao;

        import java.util.List;

public class DailyDbContorller {
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private DailyInfoDao dailyInfoDao;

    private static DailyDbContorller mDbController;

    /**
     * 获取单例
     */
    public static DailyDbContorller getInstance(Context context){
        if(mDbController == null){
            synchronized (DbController.class){
                if(mDbController == null){
                    mDbController = new DailyDbContorller(context);
                }
            }
        }
        return mDbController;
    }
    /**
     * 初始化
     * @param context
     */
    public DailyDbContorller(Context context) {
        this.context = context;
        mHelper = new DaoMaster.DevOpenHelper(context,"photo.db", null);
        mDaoMaster =new DaoMaster(getWritableDatabase());
        mDaoMaster =new DaoMaster(getReadableDatabase());
        mDaoSession = mDaoMaster.newSession();
        dailyInfoDao = mDaoSession.getDailyInfoDao();
    }
    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase(){
        if(mHelper == null){
            mHelper = new DaoMaster.DevOpenHelper(context,"photo.db",null);
        }
        SQLiteDatabase db =mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     * @return
     */
    private SQLiteDatabase getWritableDatabase(){
        if(mHelper == null){
            mHelper =new DaoMaster.DevOpenHelper(context,"photo.db",null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     * @param dailyInfo
     */
    public void insertOrReplace(DailyInfo dailyInfo){
        dailyInfoDao.insertOrReplace(dailyInfo);
    }
    /**插入一条记录，表里面要没有与之相同的记录
     *
     * @param dailyInfo
     */
    public long insert(DailyInfo dailyInfo){
        return  dailyInfoDao.insert(dailyInfo);
    }

    /**
     * 更新数据
     * @param dailyInfo
     */
    public void update(DailyInfo dailyInfo){
        DailyInfo mOldPersonInfor = dailyInfoDao.queryBuilder().where(DailyInfoDao.Properties.DailyTitle.eq(dailyInfo.getDailyTitle())).build().unique();//拿到之前的记录
        if(mOldPersonInfor !=null){
            mOldPersonInfor.setDailyTitle("张三");
            dailyInfoDao.update(mOldPersonInfor);
        }
    }
    /**
     * 按条件查询数据
     */
    public DailyInfo searchByWhere(String wherecluse){
        DailyInfo personInfors = dailyInfoDao.queryBuilder().where(DailyInfoDao.Properties.DailyTitle.eq(wherecluse)).build().unique();
        return personInfors;
    }
    /**
     * 按条件相册名查询数据
     */
    public List<DailyInfo> searchByAlbumName(String wherecluse){
        List<DailyInfo> personInfors =(List<DailyInfo>) dailyInfoDao.queryBuilder().where(DailyInfoDao.Properties.DailyDate.eq(wherecluse)).list();
        return personInfors;
    }
    /**
     * 查询所有数据
     */
    public List<DailyInfo> searchAll(){
        List<DailyInfo>personInfors=dailyInfoDao.queryBuilder().list();
        return personInfors;
    }
    /**
     * 删除数据
     */
    public void delete(String wherecluse){
        dailyInfoDao.queryBuilder().where(DailyInfoDao.Properties.DailyTitle.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }
}