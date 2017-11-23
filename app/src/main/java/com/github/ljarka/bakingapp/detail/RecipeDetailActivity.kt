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

        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, RecipeDetailFragment.newInstance())
                .commit()
    }

    companion object {
        var EXTRA_RECIPE: String = "extra_recipe"

        fun createIntent(context: Context, recipe: Recipe) {
            var intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra(EXTRA_RECIPE, recipe)
            context.startActivity(intent)
        }
    }
}
