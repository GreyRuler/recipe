package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.CookingStageInteractionListener
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.repo.CookingStageRepository
import ru.netology.nmedia.repo.impl.CookingStageRepositoryImpl
import ru.netology.nmedia.repo.impl.RecipeRepositoryImpl

class CookingStageViewModel(
    application: Application
) : AndroidViewModel(application), CookingStageInteractionListener {

    private val repository: CookingStageRepository = CookingStageRepositoryImpl.getInstance(application)

    val data by repository::data

    override fun onAddClicked(recipeId: Long, cookingStage: CookingStage) {
        repository.add(recipeId, cookingStage)
    }

    override fun onRemoveClicked(recipeId: Long, cookingStageId: Long) {
        repository.delete(recipeId, cookingStageId)
    }
}