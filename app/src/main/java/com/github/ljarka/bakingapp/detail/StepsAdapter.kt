package com.github.ljarka.bakingapp.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ljarka.bakingapp.R
import com.github.ljarka.bakingapp.network.model.Step
import kotlinx.android.synthetic.main.item_step.view.*

class StepsAdapter : RecyclerView.Adapter<StepsAdapter.StepsViewHolder>() {
    var steps: List<Step>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StepsViewHolder {
        return StepsViewHolder(LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_step, parent, false))
    }

    override fun onBindViewHolder(holder: StepsViewHolder?, position: Int) {
        holder?.itemView?.name?.text = steps?.get(position)?.shortDescription
    }

    override fun getItemCount(): Int {
        return steps?.size ?: 0
    }

    class StepsViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}