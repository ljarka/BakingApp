package com.github.ljarka.bakingapp;

import android.arch.lifecycle.ViewModel;

import com.github.ljarka.bakingapp.network.BakingService;
import com.github.ljarka.bakingapp.network.model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    private Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BakingApplication.API_URL)
            .build();


    public Observable<List<Recipe>> getRecipes() {
        return retrofit.create(BakingService.class).getRecipes();
    }
}
