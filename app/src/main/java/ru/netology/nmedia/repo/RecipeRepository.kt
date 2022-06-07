package ru.netology.nmedia.repo

import androidx.lifecycle.LiveData
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.data.Recipe
import ru.netology.nmedia.data.RecipeWithCookingStages

interface RecipeRepository {

    val data: LiveData<List<RecipeWithCookingStages>>

    fun like(recipeId: Long)
    fun delete(recipeId: Long)
    fun save(recipeWithCookingStages: RecipeWithCookingStages)

    companion object {
        const val NEW_RECIPE_ID = 0L
    }
}