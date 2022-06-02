package ru.netology.nmedia.repo

import androidx.lifecycle.LiveData
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.data.RecipeWithCookingStages

interface CookingStageRepository {

    val data: LiveData<List<RecipeWithCookingStages>>

    fun add(recipeId: Long, cookingStage: CookingStage)
    fun delete(recipeId: Long, cookingStageId: Long)

    companion object {
        const val NEW_COOKING_STAGE_ID = 0L
    }
}