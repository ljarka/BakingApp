package com.github.ljarka.bakingapp

import android.arch.lifecycle.ViewModel
import com.github.ljarka.bakingapp.network.BakingService
import com.github.ljarka.bakingapp.network.model.Recipe
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    var retrofit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://d17h27t6h515a5.cloudfront.net")
            .build()

    fun getRecipes(): Observable<List<Recipe>> {
        return retrofit.create(BakingService::class.java).getRecipes()
    }
}