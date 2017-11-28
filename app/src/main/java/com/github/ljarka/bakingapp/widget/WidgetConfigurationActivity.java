package com.github.ljarka.bakingapp.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.github.ljarka.bakingapp.MainViewModel;
import com.github.ljarka.bakingapp.OnRecipeItemClickListener;
import com.github.ljarka.bakingapp.R;
import com.github.ljarka.bakingapp.RecipesAdapter;
import com.github.ljarka.bakingapp.network.model.Recipe;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WidgetConfigurationActivity extends AppCompatActivity implements OnRecipeItemClickListener {
    private RecipesAdapter adapter = new RecipesAdapter();
    private int appWidgetId;
    private Disposable disposable = Disposables.empty();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_configuration);
        appWidgetId = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.span_count)));
        recyclerView.setAdapter(adapter);
        adapter.setOnRecipeItemClickListener(this);
        disposable = viewModel.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Recipe>>() {
                    @Override
                    public void accept(List<Recipe> recipes) throws Exception {
                        adapter.setRecipes(recipes);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(WidgetConfigurationActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    @Override
    public void onRecipeItemClick(@Nullable Recipe recipe) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putString(String.valueOf(appWidgetId), recipe.getName())
                .apply();
        IngredientsWidgetProvider.updateWidget(this, recipe.getName(), recipe.getIngredients(),
                AppWidgetManager.getInstance(this), appWidgetId);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(Activity.RESULT_OK, resultValue);
        finish();
    }
}
