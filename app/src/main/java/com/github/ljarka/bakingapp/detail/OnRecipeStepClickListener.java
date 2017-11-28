package com.github.ljarka.bakingapp.detail;

import android.support.annotation.Nullable;

import com.github.ljarka.bakingapp.network.model.Step;

public interface OnRecipeStepClickListener {

    void onRecipeStepClick(@Nullable Step step);
}
