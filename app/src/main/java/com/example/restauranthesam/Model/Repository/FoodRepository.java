package com.example.restauranthesam.Model.Repository;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.restauranthesam.Model.Api.RestaurantApi;
import com.example.restauranthesam.Model.Room.AppDataBase.FoodAppDataBase;
import com.example.restauranthesam.Model.Room.Dao.FoodDao;
import com.example.restauranthesam.Model.Room.Entity.Food;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodRepository {

    private FoodDao foodDao;
    private LiveData<List<Food>> liveData;
    private RestaurantApi api;

    public FoodRepository(Application application){
        this.foodDao = FoodAppDataBase.getInstance(application).getFoodDao();
        liveData = select();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RestaurantApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(RestaurantApi.class);
    }

    public Completable insert(Food food){
        Completable completable = new Completable() {
            @Override
            protected void subscribeActual(@NonNull CompletableObserver observer) {
                foodDao.insert(food);
                observer.onComplete();
            }
        };
        return completable;
    }

    public LiveData<List<Food>> getFoodFromWebServer(){

        LiveData<List<Food>> liveData = new LiveData<List<Food>>() {
            @Override
            public void observe(@androidx.annotation.NonNull LifecycleOwner owner, @androidx.annotation.NonNull Observer<? super List<Food>> observer) {
                super.observe(owner, observer);
                api.insertFood().enqueue(new Callback<List<Food>>() {
                    @Override
                    public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                        if (response.code() == 200){
                            observer.onChanged(response.body());
                        }
                        else{
                            observer.onChanged(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Food>> call, Throwable t) {
                        observer.onChanged(null);

                    }
                });
            }
        };
        return liveData;
    }

    public LiveData<List<Food>> select() {
        return foodDao.select();
    }
}
