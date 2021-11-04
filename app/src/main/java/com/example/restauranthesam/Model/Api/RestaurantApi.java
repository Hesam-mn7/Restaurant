package com.example.restauranthesam.Model.Api;

import com.example.restauranthesam.Model.Room.Entity.Food;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestaurantApi {

    String BASE_URL="https://run.mocky.io/";

    @GET("v3/43bdc1af-d681-460b-9154-bf0e3fc0960a")
    Call<List<Food>> insertFood();
}
