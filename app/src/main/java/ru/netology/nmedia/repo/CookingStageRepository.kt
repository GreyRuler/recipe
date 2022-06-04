package ru.netology.nmedia.repo

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.CookingStage

interface CookingStageRepository {

    val data: MutableLiveData<List<CookingStage>>

    fun addCookingStage(recipeId: Long)
    fun deleteCookingStage(cookingStageId: Long)

    companion object {
        const val NEW_COOKING_STAGE_ID = 0L
    }
}
