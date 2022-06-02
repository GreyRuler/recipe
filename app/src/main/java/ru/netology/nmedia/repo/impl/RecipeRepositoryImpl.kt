package ru.netology.nmedia.repo.impl

import android.app.Application
import androidx.lifecycle.map
import ru.netology.nmedia.data.Recipe
import ru.netology.nmedia.data.RecipeWithCookingStages
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.convertion.toEntity
import ru.netology.nmedia.db.convertion.toModel
import ru.netology.nmedia.repo.RecipeRepository

class RecipeRepositoryImpl(
    private val dao: PostDao
) : RecipeRepository {

    override val data = dao.getRecipeWithCookingStages().map { entities ->
        entities.map { it.toModel() }
    }

    override fun save(recipeWithCookingStages: RecipeWithCookingStages) {
        if (recipeWithCookingStages.recipe.id == RecipeRepository.NEW_RECIPE_ID) dao.insert(recipeWithCookingStages.toEntity())
        else dao.updateContentById(
            recipeWithCookingStages.recipe.id,
            recipeWithCookingStages.recipe.author,
            recipeWithCookingStages.recipe.nameRecipe,
            recipeWithCookingStages.recipe.category
        )
    }

    override fun like(recipeId: Long) = dao.likeById(recipeId)

    override fun delete(recipeId: Long) {
        dao.removeById(recipeId)
    }

    companion object {
        private var instance: RecipeRepositoryImpl? = null
        fun getInstance(app: Application) = instance ?: RecipeRepositoryImpl(
            dao = AppDb.getInstance(
                context = app
            ).postDao
        ).also { instance = it }
    }
}