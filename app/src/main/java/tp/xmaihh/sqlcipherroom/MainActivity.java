package tp.xmaihh.sqlcipherroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import tp.xmaihh.sqlcipherroom.adapter.AutoCompleteTextAdapter;
import tp.xmaihh.sqlcipherroom.model.User;
import tp.xmaihh.sqlcipherroom.model.dao.UserDatabase;
import tp.xmaihh.sqlcipherroom.util.UserTool;

public class MainActivity extends AppCompatActivity {
    private UserDatabase userDatabase;
    private User user;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //*
        userDatabase = UserDatabase.getINSTANCE(this);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setAdapter(new AutoCompleteTextAdapter(this));
        autoCompleteTextView.setThreshold(1);
    }

    boolean working = false;

    public void doInsertDb(View view) {
        if (!working) {
            working = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    userDatabase.beginTransaction();  //手动设置开始事务
                    long startTime = System.currentTimeMillis();   //获取开始时间
                    for (int i = 0; i < 5; i++) {
                        user = new User();
                        user.setUser_name(UserTool.getName());
                        userDatabase.daoAccess().insertOnlySingleUser(user);
                    }
                    userDatabase.setTransactionSuccessful();        //设置事务处理成功，不设置会自动回滚不提交
                    userDatabase.endTransaction();        //处理完成
                    long endTime = System.currentTimeMillis(); //获取结束时间
                    System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
                    working = false;
                }
            }).start();
            Toast.makeText(this, "成功插入5条数据", Toast.LENGTH_SHORT).show();
        }
    }
}
