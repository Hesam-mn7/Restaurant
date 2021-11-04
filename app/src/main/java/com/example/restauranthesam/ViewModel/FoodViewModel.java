package com.example.restauranthesam.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.restauranthesam.Model.Repository.FoodRepository;
import com.example.restauranthesam.Model.Room.Entity.Food;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public class FoodViewModel extends AndroidViewModel {

    private FoodRepository repository;
    private LiveData<List<Food>> liveData;


    public FoodViewModel(@NonNull Application application) {
        super(application);

        repository = new FoodRepository(application);

        liveData = repository.select();
    }

    public Completable insert(Food food){
        return repository.insert(food);
    }

    public LiveData<List<Food>> select(){
        return liveData;
    }

    public LiveData<List<Food>> getFoodFromWebServer(){
        return repository.getFoodFromWebServer();
    }
}
