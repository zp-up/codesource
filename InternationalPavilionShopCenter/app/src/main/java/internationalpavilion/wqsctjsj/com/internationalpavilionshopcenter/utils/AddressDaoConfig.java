package internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.utils;



import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

import internationalpavilion.wqsctjsj.com.internationalpavilionshopcenter.entitys.AppAddress;

/**
 * Created by wuqaing on 2018/7/7.
 */

public class AddressDaoConfig {
    private static AddressDaoConfig addressDaoConfig;

    public static AddressDaoConfig getInstance() {
        if (addressDaoConfig == null) {
            synchronized (AddressDaoConfig.class) {
                addressDaoConfig = new AddressDaoConfig();
            }
        }
        return addressDaoConfig;
    }

    public DbManager initDBManager() {
        //本地数据的初始化
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("my_address.db") //设置数据库名
                .setDbVersion(1) //设置数据库版本,每次启动应用时将会检查该版本号,
                // 发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
                .setAllowTransaction(true) //设置是否开启事务,默认为false关闭事务
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager dbManager, TableEntity<?> tableEntity) {
                    }
                })
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                // 设置数据库创建时的Listener
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                }); //设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等
        // .setDbDir(null);//设置数据库.db文件存放的目录,默认为包名下databases目录下

        DbManager db = x.getDb(daoConfig);
        return db;
    }
    public void CreateOrUpdateAddressTable(String content,String version){
        try {
            DbManager db = addressDaoConfig.initDBManager();
            List<AppAddress> appAddresses = db.findAll(AppAddress.class);
            if (appAddresses != null && appAddresses.size() != 0){
                for (int i = 0;i < 10;i++){
                    if (appAddresses.get(0).getId() == 1){
                        appAddresses.get(0).setContent(content);
                        appAddresses.get(0).setUserId(version);
                    }
                }
                db.update(appAddresses);
            }else {
                AppAddress appAddress = new AppAddress(version,content);
                db.save(appAddress);
            }
        } catch (DbException e) {
            e.printStackTrace();
            throw new DBOpenError("打开数据库失败。");
        }
    }
    public List<AppAddress> getData() {
        List<AppAddress> appAddresses = null;
        try {
            DbManager db = addressDaoConfig.initDBManager();
            appAddresses = db.findAll(AppAddress.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBOpenError("查询数据失败");
        }
        return appAddresses;
    }

}
