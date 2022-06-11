package ru.netology.nmedia.adapter

import ru.netology.nmedia.data.Recipe
import ru.netology.nmedia.data.RecipeWithCookingStages

interface RecipeInteractionListener {

    fun onFilterClicked(filterCategories: List<String>)
    fun onSearchClicked(text: String?)
    fun onFavoriteClicked(recipeWithCookingStages: RecipeWithCookingStages)
    fun onRemoveClicked(recipeWithCookingStages: RecipeWithCookingStages)
    fun onEditClicked(recipeWithCookingStages: RecipeWithCookingStages)
    fun onRecipeWithCookingStagesClicked(recipeWithCookingStages: RecipeWithCookingStages)
}