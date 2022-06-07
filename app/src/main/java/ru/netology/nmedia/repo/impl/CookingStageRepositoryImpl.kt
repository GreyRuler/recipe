package ru.netology.nmedia.repo.impl

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.repo.CookingStageRepository
import ru.netology.nmedia.repo.RecipeRepository

class CookingStageRepositoryImpl(
    cookingStages: List<CookingStage>
) : CookingStageRepository {

    private val cookingStages
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data: MutableLiveData<List<CookingStage>> = MutableLiveData(cookingStages)

    override fun addCookingStage(nextCookingStageId: Long) {
        Log.d("nextId", nextCookingStageId.toString())
        data.value =  cookingStages + listOf(
            CookingStage(
                id = nextCookingStageId,
                recipeId = RecipeRepository.NEW_RECIPE_ID,
                name = ""
            )
        )
    }

    override fun deleteCookingStage(cookingStageId: Long) {
        data.value = if (cookingStages.size > 1) cookingStages.filterNot {
            it.id == cookingStageId
        } else return
    }
}