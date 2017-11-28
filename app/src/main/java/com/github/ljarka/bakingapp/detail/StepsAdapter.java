package com.github.ljarka.bakingapp.detail;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ljarka.bakingapp.GlideApp;
import com.github.ljarka.bakingapp.R;
import com.github.ljarka.bakingapp.network.model.Step;

import java.util.Collections;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    private OnRecipeStepClickListener onRecipeStepClickListener;
    private List<Step> steps = Collections.emptyList();

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StepsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false));
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        int number = position + 1;
        Step step = steps.get(position);
        String description = step.getShortDescription();
        holder.name.setText(number + ". " + description);
        GlideApp.with(holder.image)
                .load(step.getThumbnailURL())
                .placeholder(
                        new ColorDrawable(ContextCompat.getColor(holder.image.getContext(), R.color.cardview_light_background)))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void setOnRecipeStepClickListener(OnRecipeStepClickListener onRecipeStepClickListener) {
        this.onRecipeStepClickListener = onRecipeStepClickListener;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView image;

        public StepsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
        }

        @Override
        public void onClick(View view) {
            onRecipeStepClickListener.onRecipeStepClick(steps.get(getAdapterPosition()));
        }
    }
}
