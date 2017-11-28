package com.github.ljarka.bakingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ljarka.bakingapp.network.model.Recipe;

import java.util.Collections;
import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {
    private List<Recipe> recipes = Collections.emptyList();
    private OnRecipeItemClickListener onRecipeItemClickListener;

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        holder.name.setText(recipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public void setOnRecipeItemClickListener(OnRecipeItemClickListener onRecipeItemClickListener) {
        this.onRecipeItemClickListener = onRecipeItemClickListener;
    }

    class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;

        public RecipesViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRecipeItemClickListener.onRecipeItemClick(recipes.get(getAdapterPosition()));
        }
    }
}
