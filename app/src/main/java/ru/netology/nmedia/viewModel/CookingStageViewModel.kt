package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.CookingStageInteractionListener
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.repo.CookingStageRepository
import ru.netology.nmedia.repo.RecipeRepository
import ru.netology.nmedia.repo.impl.CookingStageRepositoryImpl

class CookingStageViewModel(
    cookingStages: List<CookingStage>?,
    recipeId: Long?
) : ViewModel(), CookingStageInteractionListener {

    private val repository: CookingStageRepository =
        cookingStages?.let {
            CookingStageRepositoryImpl(it)
        } ?: CookingStageRepositoryImpl(
            listOf(
                CookingStage(
                    id = CookingStageRepository.NEW_COOKING_STAGE_ID,
                    recipeId = recipeId ?: RecipeRepository.NEW_RECIPE_ID,
                    name = ""
                )
            )
        )

    val data by repository::data

    override fun onAddClicked(recipeId: Long) {
        repository.addCookingStage(recipeId)
    }

    override fun onRemoveClicked(cookingStageId: Long) {
        repository.deleteCookingStage(cookingStageId)
    }
}