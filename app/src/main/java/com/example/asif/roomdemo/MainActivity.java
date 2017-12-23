package com.example.asif.roomdemo;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.asif.roomdemo.adapter.UserAdapter;
import com.example.asif.roomdemo.config.AppConfig;
import com.example.asif.roomdemo.database.AppDatabase;
import com.example.asif.roomdemo.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";

    RecyclerView rvUsers;
    RecyclerView.Adapter adapter;
    EditText etName,etEmail;
    Button btnAdd;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvUsers=findViewById(R.id.rvUsers);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        btnAdd=findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, AppConfig.DB_NAME)
                .allowMainThreadQueries()
                .build();

//        db.userDao().insertAll(new User("Samim","sam91v@gmail.com"));

//        for (User user:db.userDao().getAll()){
//            db.userDao().delete(user);
//        }

        List<User> users=db.userDao().getAll();
        adapter=new UserAdapter(this,users);
        rvUsers.setAdapter(adapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        if(db.isOpen()){
            Log.d(TAG, "onCreate: database opened");
        }
        else {
            Log.d(TAG, "onCreate: database Closed");
        }
//        db.userDao().insertAll(new User(1,"Asif","Mohammad","Mollah"));
//        db.close();
    }

    private void load(){
        List<User> users=db.userDao().getAll();
        adapter=new UserAdapter(this,users);
        rvUsers.setAdapter(adapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));
        rvUsers.scrollToPosition(adapter.getItemCount()-1);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btnAdd){
            db.userDao().insertAll(new User(etName.getText().toString(),etEmail.getText().toString()));
            etName.setText("");
            etEmail.setText("");
            etName.requestFocus();
            load();
        }
    }
}
