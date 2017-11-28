package com.github.ljarka.bakingapp;

import android.support.annotation.Nullable;

import com.github.ljarka.bakingapp.network.model.Recipe;

public interface OnRecipeItemClickListener {
    void onRecipeItemClick(@Nullable Recipe recipe);
}
