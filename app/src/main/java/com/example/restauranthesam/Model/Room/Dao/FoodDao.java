package com.example.restauranthesam.Model.Room.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.restauranthesam.Model.Room.Entity.Food;

import java.util.List;


@Dao
public interface FoodDao {

    @Insert
    void insert(Food food);

    @Query("select * from Food order by id desc")
    LiveData<List<Food>> select();
}
