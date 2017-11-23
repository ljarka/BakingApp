package com.github.ljarka.bakingapp.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ljarka.bakingapp.R
import com.github.ljarka.bakingapp.network.model.Recipe
import kotlinx.android.synthetic.main.fragment_recipe_detail.view.*

class RecipeDetailFragment : Fragment() {
    private var ingredientsAdapter: IngredientsAdapter = IngredientsAdapter()
    private var stepsAdapter: StepsAdapter = StepsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recipe: Recipe? = arguments?.getParcelable(EXTRA_RECIPE)

        view.ingredientsRecyclerView.adapter = ingredientsAdapter
        ingredientsAdapter.ingredients = recipe?.ingredients
        view.ingredientsRecyclerView.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        view.stepsRecyclerView.adapter = stepsAdapter
        stepsAdapter.steps = recipe?.steps
        view.stepsRecyclerView.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        private val EXTRA_RECIPE = "extra_recipe"

        fun newInstance(recipe: Recipe): Fragment {
            val recipeDetailFragment = RecipeDetailFragment()
            val arguments = Bundle()
            arguments.putParcelable(EXTRA_RECIPE, recipe)
            recipeDetailFragment.arguments = arguments
            return recipeDetailFragment
        }
    }
}