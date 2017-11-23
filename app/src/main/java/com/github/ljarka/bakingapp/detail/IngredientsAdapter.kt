package com.github.ljarka.bakingapp.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ljarka.bakingapp.R
import com.github.ljarka.bakingapp.network.model.IngredientData
import kotlinx.android.synthetic.main.item_ingredient.view.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {
    var ingredients: List<IngredientData>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_ingredient, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder?, position: Int) {
        holder?.itemView?.name?.text = ingredients?.get(position)?.ingredient
    }

    override fun getItemCount(): Int {
        return ingredients?.size ?: 0
    }

    class IngredientsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}