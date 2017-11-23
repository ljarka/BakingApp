package com.github.ljarka.bakingapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.github.ljarka.bakingapp.detail.RecipeDetailActivity
import com.github.ljarka.bakingapp.network.model.Recipe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnRecipeItemClickListener {
    private val adapter: RecipesAdapter = RecipesAdapter()

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.onRecipeItemClickListener = this
        viewModel.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.recipes = it
                }, {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
                })
    }

    override fun onRecipeItemClick(recipe: Recipe?) {
        if (recipe != null) {
            RecipeDetailActivity.createIntent(this, recipe)
        }
    }
}
