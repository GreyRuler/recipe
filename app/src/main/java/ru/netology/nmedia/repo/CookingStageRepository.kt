package ru.netology.nmedia.repo

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.CookingStage

interface CookingStageRepository {

    val data: MutableLiveData<List<CookingStage>>

    fun addCookingStage(nextCookingStageId: Long)
    fun deleteCookingStage(cookingStageId: Long)
}
