package ru.netology.nmedia.repo.impl

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.nmedia.data.RecipeWithCookingStages
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.convertion.toModel

class RecipeRepositoryImpl(dao: PostDao) : AbstractRecipeRepository(dao) {

    override val data = dao.getRecipeWithCookingStages().map { entities ->
        entities.map { it.toModel() }.reversed()
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