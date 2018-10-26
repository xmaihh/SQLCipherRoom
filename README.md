# SQLCipherRoom
[Room](https://developer.android.com/topic/libraries/architecture/adding-components#room)作为Android平台的一款对象关系映射框架
并不支持[sqlcipher](https://www.zetetic.net/sqlcipher/sqlcipher-for-android/)，需要我们使用[第三方库](https://github.com/commonsguy/cwac-saferoom)来支持

下面演示了从/data/data/<package_name>/databases目录下面导出的DB文件通过sqlcipher加密后打开情况

![img](https://github.com/xmaihh/SQLCipherRoom/raw/master/art/encrypt.png)