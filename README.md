# SQLCipherRoom
[Room](https://developer.android.com/topic/libraries/architecture/adding-components#room)作为Android平台的一款对象关系映射框架
并不支持[sqlcipher](https://www.zetetic.net/sqlcipher/sqlcipher-for-android/)，需要我们使用[第三方库](https://github.com/commonsguy/cwac-saferoom)来支持
对使用Room生成的数据库加密方法集成方法
```
 private static SafeHelperFactory factory = new SafeHelperFactory(passphrase);

    public static UserDatabase getINSTANCE(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(),
                                UserDatabase.class, DATABASE_NAME)
                                .openHelperFactory(factory)  //encrypt
                                .fallbackToDestructiveMigration()
                                .build();
            }
            return INSTANCE;
        }
    }
```

下面演示了从/data/data/<package_name>/databases目录下面导出的DB文件通过sqlcipher加密后打开情况

![img](https://github.com/xmaihh/SQLCipherRoom/raw/master/art/encrypt.png)

也可直接对```xxx.db```文件直接加密
```
SQLCipherUtils.getDatabaseState(File);  // 判断文件是否加密 ENCRYPTED, UNENCRYPTED, or DOES_NOT_EXIST

SQLCipherUtils.encrypt(Context, File, char[]); // 加密

```