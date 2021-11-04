
package com.example.restauranthesam.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restauranthesam.Model.Room.Entity.Food;
import com.example.restauranthesam.ViewModel.FoodViewModel;
import com.example.restauranthesam.databinding.ItemFoodBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    private FoodViewModel viewModel;
    private List<Food> list;

    Context context;

    public FoodAdapter(FoodViewModel foodViewModel, Context context) {
        this.list = new ArrayList<>();
        this.viewModel = foodViewModel;
        this.context = context;
    }

    public void setList(List<Food> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding binding = ItemFoodBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Food food = this.list.get(position);

        int id = food.getId();
        String foodName = food.getFoodName();
        String foodImg = food.getFoodImg();
        String foodDetail = food.getFoodDetail();
        String foodPrice = food.getFoodPrice();
        String foodStar = food.getFoodStar();
        String foodType = food.getFoodType();

        holder.binding.tvFoodName.setText(foodName);
        holder.binding.tvFoodDetail.setText(foodDetail);
        holder.binding.tvFoodPrice.setText(foodPrice);

        Picasso.get()
                .load(foodImg)
                .into(holder.binding.FoodImg);

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ItemFoodBinding binding;
        public MyViewHolder(@NonNull ItemFoodBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
