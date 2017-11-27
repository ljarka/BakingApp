package com.github.ljarka.bakingapp.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.widget.RemoteViews
import com.github.ljarka.bakingapp.BakingApplication
import com.github.ljarka.bakingapp.R
import com.github.ljarka.bakingapp.network.BakingService
import com.github.ljarka.bakingapp.network.model.IngredientData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class IngredientsWidgetProvider : AppWidgetProvider() {

    var retrofit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BakingApplication.API_URL)
            .build()

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        if (appWidgetIds != null) {
            for (appWidgetId in appWidgetIds) {
                val recipeName = preferences.getString(appWidgetId.toString(), "")

                if (recipeName.isNotEmpty()) {
                    retrofit.create(BakingService::class.java)
                            .getRecipes()
                            .flatMap {
                                Observable.fromIterable(it)
                            }
                            .filter({
                                it.name == recipeName
                            }).firstElement()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                updateWidget(context, recipeName, it.ingredients, appWidgetManager, appWidgetId)
                            })
                }

            }
        }
    }

    companion object {
        fun updateWidget(context: Context?, recipeName: String?, ingredients: List<IngredientData>?,
                appWidgetManager: AppWidgetManager?, appWidgetId: Int?) {
            val views = RemoteViews(context?.packageName, R.layout.widget_ingredients)
            views.setTextViewText(R.id.name, recipeName)
            views.removeAllViews(R.id.itemsContainer)

            if (ingredients != null) {
                for (ingredient in ingredients) {
                    val ingredientView = RemoteViews(context?.packageName, R.layout.item_ingredient)
                    ingredientView.setTextViewText(R.id.name, ingredient.ingredient)
                    views.addView(R.id.itemsContainer, ingredientView)
                }
            }

            if (appWidgetId != null) {
                appWidgetManager?.updateAppWidget(appWidgetId, views)
            }

        }
    }
}