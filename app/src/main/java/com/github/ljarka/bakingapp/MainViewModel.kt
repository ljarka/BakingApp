package com.github.ljarka.bakingapp

import android.arch.lifecycle.ViewModel
import com.github.ljarka.bakingapp.network.BakingService
import com.github.ljarka.bakingapp.network.model.Recipe
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    private var retrofit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BakingApplication.API_URL)
            .build()

    fun getRecipes(): Observable<List<Recipe>> {
        return retrofit.create(BakingService::class.java).getRecipes()
    }
}