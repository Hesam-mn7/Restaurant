package com.example.restauranthesam.View.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;

import com.example.restauranthesam.Adapter.FoodAdapter;
import com.example.restauranthesam.Adapter.ImageModel;
import com.example.restauranthesam.Adapter.SlidingImage_Adapter;
import com.example.restauranthesam.Const;
import com.example.restauranthesam.Model.Room.Entity.Food;
import com.example.restauranthesam.R;
import com.example.restauranthesam.ViewModel.FoodViewModel;
import com.example.restauranthesam.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    FoodViewModel foodViewModel;

    FoodAdapter foodAdapter;


//    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.slide1, R.drawable.slide2,
            R.drawable.slide1,R.drawable.slide2 , R.drawable.slide3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        foodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);
        initRecycleViewFood();
        getFoodFromWebServer();
        setupNavigationView();
        setupToolbar();
        slideshow();
    }

    private void initRecycleViewFood() {

        foodAdapter = new FoodAdapter(foodViewModel,this);

        binding.recycleePortarafdar.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        binding.recycleePortarafdar.setAdapter(foodAdapter);
        foodViewModel.select().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                foodAdapter.setList(foods);
            }
        });

        binding.recycleIraniFood.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        binding.recycleIraniFood.setAdapter(foodAdapter);
        foodViewModel.select().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                foodAdapter.setList(foods);
            }
        });

        binding.recycleFastFood.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        binding.recycleFastFood.setAdapter(foodAdapter);
        foodViewModel.select().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foods) {
                foodAdapter.setList(foods);
            }
        });
    }

    private void getFoodFromWebServer() {

        String result = getSharedPreferenceFood(Const.SHARED_PERF_KEY_FOOD);
        if (result == null){
            foodViewModel.getFoodFromWebServer().observe(this, new Observer<List<Food>>() {
                @Override
                public void onChanged(List<Food> foods) {
                    for (Food item : foods){
                        Food food = new Food(item.getId(),item.getFoodName(),item.getFoodImg()
                        ,item.getFoodDetail(),item.getFoodPrice(),item.getFoodStar(),item.getFoodType());

                        foodViewModel.insert(food)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onComplete() {
                                        setPrefernceFood(Const.SHARED_PERF_KEY_FOOD,"finish");

                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {

                                    }
                                });
                    }
                }
            });
        }
    }

    public void setPrefernceFood(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SHARED_PREF_NAME_FOOD, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getSharedPreferenceFood(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(Const.SHARED_PREF_NAME_FOOD, MODE_PRIVATE);
        if (!sharedPreferences.contains(key)) {
            return null;
        }
        return sharedPreferences.getString(key, null);
    }

    private void setupNavigationView() {
        binding.navViewProfile.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@androidx.annotation.NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_settings:
                        Snackbar.make(binding.navViewProfile, "Settings", Snackbar.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_archive:
                        Snackbar.make(binding.navViewProfile, "Archive", Snackbar.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_close_friends:
                        Snackbar.make(binding.navViewProfile, "Close Freinds", Snackbar.LENGTH_SHORT).show();
                        break;

                    case R.id.nav_covid19:
                        Snackbar.make(binding.navViewProfile, "COVID-19 Information", Snackbar.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    private void setupToolbar() {
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.design_default_color_primary_variant));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                binding.drawerLayoutProfileActivity, binding.toolbar, 0, 0);
        binding.drawerLayoutProfileActivity.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }


    private ArrayList<ImageModel> populateList(){

        ArrayList<ImageModel> list = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }
        return list;
    }

    private void slideshow() {

        //slideShow
        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();


        binding.pager.setAdapter(new SlidingImage_Adapter(MainActivity.this,imageModelArrayList));


//        final CirclePageIndicator indicator = findViewById(R.id.indicator);

        binding.indicator.setViewPager(binding.pager);

        final float density = getResources().getDisplayMetrics().density;

//        Set circle indicator radius
        binding.indicator.setRadius(4 * density);

        NUM_PAGES =imageModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                binding.pager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator

        binding.indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = position;

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }
}