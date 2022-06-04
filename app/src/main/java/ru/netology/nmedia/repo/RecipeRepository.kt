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
//    fun addCookingStage(recipeId: Long, cookingStage: CookingStage)
//    fun deleteCookingStage(recipeId: Long, cookingStageId: Long)

    companion object {
        const val NEW_RECIPE_ID = 0L
        const val NEW_COOKING_STAGE_ID = 0L
    }
}