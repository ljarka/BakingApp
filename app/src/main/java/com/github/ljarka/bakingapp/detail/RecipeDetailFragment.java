package com.github.ljarka.bakingapp.detail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ljarka.bakingapp.R;
import com.github.ljarka.bakingapp.network.model.Recipe;

public class RecipeDetailFragment extends Fragment {
    public static final String FRAGMENT_TAG = "RecipeDetailFragment";
    public static final String EXTRA_RECIPE = "extra_recipe";

    private IngredientsAdapter ingredientsAdapter = new IngredientsAdapter();
    private StepsAdapter stepsAdapter = new StepsAdapter();
    private OnRecipeStepClickListener onRecipeStepClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeStepClickListener) {
            onRecipeStepClickListener = (OnRecipeStepClickListener) context;
        } else {
            throw new IllegalStateException("Activity has to implement " + OnRecipeStepClickListener.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Recipe recipe = getArguments().getParcelable(EXTRA_RECIPE);
        RecyclerView ingredientsRecyclerView = view.findViewById(R.id.ingredientsRecyclerView);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        ingredientsAdapter.setIngredients(recipe.getIngredients());
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        RecyclerView stepsRecyclerView = view.findViewById(R.id.stepsRecyclerView);
        stepsRecyclerView.setAdapter(stepsAdapter);
        stepsAdapter.setOnRecipeStepClickListener(onRecipeStepClickListener);
        stepsAdapter.setSteps(recipe.getSteps());
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    public static Fragment newInstance(@NonNull Recipe recipe) {
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(EXTRA_RECIPE, recipe);
        recipeDetailFragment.setArguments(arguments);
        return recipeDetailFragment;
    }
}
