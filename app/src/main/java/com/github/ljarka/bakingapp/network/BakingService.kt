package com.github.ljarka.bakingapp.network

import com.github.ljarka.bakingapp.network.model.Recipe
import io.reactivex.Observable
import retrofit2.http.GET

interface BakingService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    fun getRecipes(): Observable<List<Recipe>>
}