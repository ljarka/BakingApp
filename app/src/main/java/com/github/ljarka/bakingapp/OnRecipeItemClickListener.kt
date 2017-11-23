package com.github.ljarka.bakingapp

import com.github.ljarka.bakingapp.network.model.Recipe

interface OnRecipeItemClickListener {
    fun onRecipeItemClick(recipe: Recipe?)
}