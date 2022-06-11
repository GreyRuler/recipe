package ru.netology.nmedia.repo

import androidx.lifecycle.LiveData
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.data.Recipe
import ru.netology.nmedia.data.RecipeWithCookingStages
import ru.netology.nmedia.util.SingleLiveEvent

interface RecipeRepository {

    val data: LiveData<List<RecipeWithCookingStages>>

    fun like(recipeId: Long)
    fun delete(recipeId: Long)
    fun save(recipeWithCookingStages: RecipeWithCookingStages)
    fun search(text: String?): List<RecipeWithCookingStages>?
    fun filter(filterCategories: List<String>): List<RecipeWithCookingStages>?

    companion object {
        const val NEW_RECIPE_ID = 0L
    }
}