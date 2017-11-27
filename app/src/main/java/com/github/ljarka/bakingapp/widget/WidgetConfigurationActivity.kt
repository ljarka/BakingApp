package com.github.ljarka.bakingapp.widget

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.appwidget.AppWidgetManager
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.github.ljarka.bakingapp.MainViewModel
import com.github.ljarka.bakingapp.OnRecipeItemClickListener
import com.github.ljarka.bakingapp.R
import com.github.ljarka.bakingapp.RecipesAdapter
import com.github.ljarka.bakingapp.network.model.Recipe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class WidgetConfigurationActivity : AppCompatActivity(), OnRecipeItemClickListener {
    private val adapter: RecipesAdapter = RecipesAdapter()
    private var appWidgetId: Int? = null
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widget_configuration)
        appWidgetId = intent.extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        recyclerView.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.span_count))
        recyclerView.adapter = adapter
        adapter.onRecipeItemClickListener = this
        viewModel.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.recipes = it
                }, {
                    Toast.makeText(this@WidgetConfigurationActivity, "Error", Toast.LENGTH_LONG).show()
                })
    }

    override fun onRecipeItemClick(recipe: Recipe?) {
        PreferenceManager
                .getDefaultSharedPreferences(this)
                .edit().putString(appWidgetId.toString(), recipe?.name)
                .apply()
        IngredientsWidgetProvider.updateWidget(this, recipe?.name, recipe?.ingredients, AppWidgetManager.getInstance(this),
                appWidgetId)
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }
}