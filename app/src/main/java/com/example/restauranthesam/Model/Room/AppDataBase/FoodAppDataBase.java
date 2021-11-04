package com.example.restauranthesam.Model.Room.AppDataBase;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.restauranthesam.Model.Room.Dao.FoodDao;
import com.example.restauranthesam.Model.Room.Entity.Food;

@Database(entities = Food.class , version = 1)
public abstract class FoodAppDataBase extends RoomDatabase {

    public abstract FoodDao getFoodDao();

    private static FoodAppDataBase instance;

    public static FoodAppDataBase getInstance(Application application){
       if (instance==null){
           instance = Room.databaseBuilder(application,FoodAppDataBase.class, "Food.DB").build();


       }
       return instance;
    }
}
