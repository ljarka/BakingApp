package com.github.ljarka.bakingapp.detail;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.github.ljarka.bakingapp.R;
import com.github.ljarka.bakingapp.network.model.Recipe;
import com.github.ljarka.bakingapp.network.model.Step;

public class RecipeDetailActivity extends AppCompatActivity implements OnRecipeStepClickListener {
    private static final String EXTRA_RECIPE = "extra_recipe";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
        getSupportActionBar().setTitle(recipe.getName());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, RecipeDetailFragment.newInstance(recipe), RecipeDetailFragment.FRAGMENT_TAG)
                    .commit();
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

            if (fragment instanceof RecipeDetailStepFragment
                    && getResources().getBoolean(R.bool.tablet)
                    && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, RecipeDetailFragment.newInstance(recipe),
                                RecipeDetailFragment.FRAGMENT_TAG)
                        .commit();
            }
        }
    }

    @Override
    public void onRecipeStepClick(@Nullable Step step) {
        if (findViewById(R.id.secondFragmentContainer) == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, RecipeDetailStepFragment.newInstance(step),
                            RecipeDetailFragment.FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.secondFragmentContainer, RecipeDetailStepFragment.newInstance(step),
                            RecipeDetailStepFragment.FRAGMENT_TAG)
                    .commit();
        }
    }

    public static Intent createIntent(@NonNull Context context, @NonNull Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        return intent;
    }
}
