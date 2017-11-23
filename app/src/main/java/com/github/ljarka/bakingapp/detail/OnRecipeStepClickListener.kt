package com.github.ljarka.bakingapp.detail

import com.github.ljarka.bakingapp.network.model.Step

interface OnRecipeStepClickListener {
    fun onRecipeStepClick(step: Step?)
}