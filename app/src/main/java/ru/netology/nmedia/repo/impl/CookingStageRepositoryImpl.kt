package ru.netology.nmedia.repo.impl

import android.app.Application
import androidx.lifecycle.map
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.convertion.toModel
import ru.netology.nmedia.repo.CookingStageRepository

class CookingStageRepositoryImpl(
    private val dao: PostDao
) : CookingStageRepository {

    override val data = dao.getRecipeWithCookingStages().map { entities ->
        entities.map { it.toModel() }
    }

    override fun add(recipeId: Long, cookingStage: CookingStage) {
        data.value?.find { recipeWithCookingStages ->
            recipeWithCookingStages.recipe.id == recipeId
        }?.cookingStages?.plus(CookingStage(
            id = CookingStageRepository.NEW_COOKING_STAGE_ID,
            recipeId = CookingStageRepository.NEW_COOKING_STAGE_ID,
            name = ""
        ))
    }

    override fun delete(recipeId: Long, cookingStageId: Long) {
        data.value?.find { recipeWithCookingStages ->
            recipeWithCookingStages.recipe.id == recipeId
        }?.cookingStages?.also { cookingStages ->
            cookingStages.filter {
                it.id != cookingStageId
            }
        }
    }

    companion object {
        private var instance: CookingStageRepositoryImpl? = null
        fun getInstance(app: Application) = instance ?: CookingStageRepositoryImpl(
            dao = AppDb.getInstance(
                context = app
            ).postDao
        ).also { instance = it }
    }
}