package com.example.restauranthesam.Model.Room.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Food {

    @PrimaryKey(autoGenerate  = true)
    private int id;

    @ColumnInfo
    private String FoodName;

    @ColumnInfo
    private String FoodImg;

    @ColumnInfo
    private String FoodDetail;

    @ColumnInfo
    private String FoodPrice;

    @ColumnInfo
    private String FoodStar;

    @ColumnInfo
    private String FoodType;

    public Food(){

    }

    public Food(int id, String foodName, String foodImg, String foodDetail,
                String foodPrice, String foodStar, String foodType) {
        this.id = id;
        FoodName = foodName;
        FoodImg = foodImg;
        FoodDetail = foodDetail;
        FoodPrice = foodPrice;
        FoodStar = foodStar;
        FoodType = foodType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getFoodImg() {
        return FoodImg;
    }

    public void setFoodImg(String foodImg) {
        FoodImg = foodImg;
    }

    public String getFoodDetail() {
        return FoodDetail;
    }

    public void setFoodDetail(String foodDetail) {
        FoodDetail = foodDetail;
    }

    public String getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        FoodPrice = foodPrice;
    }

    public String getFoodStar() {
        return FoodStar;
    }

    public void setFoodStar(String foodStar) {
        FoodStar = foodStar;
    }

    public String getFoodType() {
        return FoodType;
    }

    public void setFoodType(String foodType) {
        FoodType = foodType;
    }
}
