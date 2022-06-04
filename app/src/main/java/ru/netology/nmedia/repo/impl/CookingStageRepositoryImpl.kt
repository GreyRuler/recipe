package ru.netology.nmedia.repo.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.repo.CookingStageRepository

class CookingStageRepositoryImpl(
    cookingStages: List<CookingStage>
) : CookingStageRepository {

    private var nextId = CookingStageRepository.NEW_COOKING_STAGE_ID

    private val cookingStages
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data: MutableLiveData<List<CookingStage>> = MutableLiveData(cookingStages)

    override fun addCookingStage(recipeId: Long) {
        data.value = cookingStages + listOf(
            CookingStage(
                id = ++nextId,
                recipeId = recipeId,
                name = ""
            )
        )
    }

    override fun deleteCookingStage(cookingStageId: Long) {
        Log.d("cookingStages size", cookingStages.size.toString())
        data.value =
            if (cookingStages.size > 1) cookingStages.filterNot { it.id == cookingStageId } else return
    }
}