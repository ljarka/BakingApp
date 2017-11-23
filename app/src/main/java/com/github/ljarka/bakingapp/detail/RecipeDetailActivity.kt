package com.github.ljarka.bakingapp.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.ljarka.bakingapp.R
import com.github.ljarka.bakingapp.network.model.Recipe

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        val recipe: Recipe = intent.getParcelableExtra(EXTRA_RECIPE)
        supportActionBar?.title = recipe.name
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, RecipeDetailFragment.newInstance(recipe))
                .commit()
    }

    companion object {
        private val EXTRA_RECIPE = "extra_recipe"

        fun createIntent(context: Context, recipe: Recipe) {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra(EXTRA_RECIPE, recipe)
            context.startActivity(intent)
        }
    }
}
