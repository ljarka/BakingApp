package com.github.ljarka.bakingapp.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.ljarka.bakingapp.R
import com.github.ljarka.bakingapp.network.model.Recipe
import com.github.ljarka.bakingapp.network.model.Step

class RecipeDetailActivity : AppCompatActivity(), OnRecipeStepClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
        val recipe: Recipe = intent.getParcelableExtra(EXTRA_RECIPE)
        supportActionBar?.title = recipe.name

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, RecipeDetailFragment.newInstance(recipe), RecipeDetailFragment.FRAGMENT_TAG)
                    .commit()
        } else {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

            if (fragment is RecipeDetailStepFragment) {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, RecipeDetailFragment.newInstance(recipe),
                                RecipeDetailFragment.FRAGMENT_TAG)
                        .commit()
            }
        }
    }

    override fun onRecipeStepClick(step: Step?) {
        if (findViewById<View>(R.id.secondFragmentContainer) == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, RecipeDetailStepFragment.newInstance(step),
                            RecipeDetailFragment.FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit()
        } else {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.secondFragmentContainer, RecipeDetailStepFragment.newInstance(step),
                            RecipeDetailStepFragment.FRAGMENT_TAG)
                    .commit()
        }
    }

    companion object {
        const val EXTRA_RECIPE = "extra_recipe"

        fun createIntent(context: Context, recipe: Recipe) {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra(EXTRA_RECIPE, recipe)
            context.startActivity(intent)
        }
    }
}
