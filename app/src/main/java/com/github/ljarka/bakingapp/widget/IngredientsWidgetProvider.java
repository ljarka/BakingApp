package com.github.ljarka.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.github.ljarka.bakingapp.BakingApplication;
import com.github.ljarka.bakingapp.R;
import com.github.ljarka.bakingapp.network.BakingService;
import com.github.ljarka.bakingapp.network.model.IngredientData;
import com.github.ljarka.bakingapp.network.model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class IngredientsWidgetProvider extends AppWidgetProvider {
    private Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BakingApplication.API_URL)
            .build();

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (appWidgetIds != null) {
            for (final int appWidgetId : appWidgetIds) {
                final String recipeName = preferences.getString(String.valueOf(appWidgetId), "");

                if (!TextUtils.isEmpty(recipeName)) {
                    retrofit.create(BakingService.class)
                            .getRecipes()
                            .flatMap(new Function<List<Recipe>, ObservableSource<Recipe>>() {
                                @Override
                                public ObservableSource<Recipe> apply(List<Recipe> recipes) throws Exception {
                                    return Observable.fromIterable(recipes);
                                }
                            })
                            .filter(new Predicate<Recipe>() {
                                @Override
                                public boolean test(Recipe recipe) throws Exception {
                                    return recipe.getName().equals(recipeName);
                                }
                            })
                            .firstElement()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Recipe>() {
                                @Override
                                public void accept(Recipe recipe) throws Exception {
                                    updateWidget(context, recipeName, recipe.getIngredients(), appWidgetManager, appWidgetId);
                                }
                            });
                }
            }
        }
    }

    public static void updateWidget(@Nullable Context context, @Nullable String recipeName,
            @Nullable List<IngredientData> ingredients, @Nullable AppWidgetManager appWidgetManager, Integer appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients);
        views.setTextViewText(R.id.name, recipeName);
        views.removeAllViews(R.id.itemsContainer);

        if (ingredients != null) {
            for (IngredientData ingredient : ingredients) {
                RemoteViews ingredientView = new RemoteViews(context.getPackageName(), R.layout.item_ingredient);
                ingredientView.setTextViewText(R.id.name, ingredient.getIngredient());
                views.addView(R.id.itemsContainer, ingredientView);
            }
        }

        if (appWidgetId != null) {
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}

