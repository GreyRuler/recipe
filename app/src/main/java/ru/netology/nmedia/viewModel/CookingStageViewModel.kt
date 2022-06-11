package ru.netology.nmedia.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.CookingStageInteractionListener
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.repo.CookingStageRepository
import ru.netology.nmedia.repo.RecipeRepository
import ru.netology.nmedia.repo.impl.CookingStageRepositoryImpl

class CookingStageViewModel(
    cookingStages: List<CookingStage>?,
    context: Context
) : ViewModel(), CookingStageInteractionListener {

    private var nextCookingStageId = AppDb.getInstance(
        context = context
    ).postDao.getMaxIdCookingStage()

    private val repository: CookingStageRepository =
        cookingStages?.let {
            CookingStageRepositoryImpl(it)
        } ?: CookingStageRepositoryImpl(
            listOf(
                CookingStage(
                    id = ++nextCookingStageId,
                    recipeId = RecipeRepository.NEW_RECIPE_ID,
                    name = ""
                )
            )
        )

    val data by repository::data

    override fun onAddClicked(recipeId: Long) {
        repository.addCookingStage(++nextCookingStageId)
    }

    override fun onRemoveClicked(cookingStageId: Long) {
        repository.deleteCookingStage(cookingStageId)
    }
}