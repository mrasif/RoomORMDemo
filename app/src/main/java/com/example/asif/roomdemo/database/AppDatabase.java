package com.example.asif.roomdemo.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.asif.roomdemo.dao.UserDao;
import com.example.asif.roomdemo.models.User;

/**
 * Created by asif on 12/21/17.
 */

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
