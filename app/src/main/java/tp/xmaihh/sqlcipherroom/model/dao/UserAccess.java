package tp.xmaihh.sqlcipherroom.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import tp.xmaihh.sqlcipherroom.model.User;

@Dao
public interface UserAccess {
    @Insert
    void insertOnlySingleUser(User user);

    @Insert
    void insertMultipleUser(List<User> userList);

    @Query("SELECT*FROM user WHERE Id =:userId")
    User fetchOneUserbyUserId(int userId);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}
