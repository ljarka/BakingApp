package com.github.ljarka.bakingapp.network.model

data class Recipe(val id: Int, val ingredients: List<IngredientData>, val steps: List<Step>, val name: String, val servings: Int,
        val image: String)

data class IngredientData(val quantity: Float, val measure: String, val ingredient: String)

data class Step(val id: Int, val shortDescription: String, val description: String, val videoURL: String,
        val thumbnailURL: String)