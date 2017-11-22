package com.github.ljarka.bakingapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ljarka.bakingapp.network.model.Recipe
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {
    var recipes: List<Recipe>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_recipe, parent, false))
    }

    override fun onBindViewHolder(holder: RecipesViewHolder?, position: Int) {
        holder?.itemView?.name?.text = recipes?.get(position)?.name
    }

    override fun getItemCount(): Int {
        return recipes?.size ?: 0
    }

    class RecipesViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}