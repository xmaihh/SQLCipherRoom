package tp.xmaihh.sqlcipherroom.model.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.commonsware.cwac.saferoom.SafeHelperFactory;

import tp.xmaihh.sqlcipherroom.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase INSTANCE;
    private static final Object sLock = new Object();
    private static final String DATABASE_NAME = "userDb";
    private static final char[] passphrase = {'m', 'a', 'r', 'k'};

    public abstract UserAccess daoAccess();

    //    private static SafeHelperFactory factory = SafeHelperFactory.fromUser();
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
}
