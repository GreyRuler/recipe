package ru.netology.nmedia.repo.impl

import android.app.Application
import androidx.lifecycle.map
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.convertion.toModel

class FavoriteRecipeRepositoryImpl(dao: PostDao) : AbstractRecipeRepository(dao) {

    override val data = dao.getFavoriteRecipeWithCookingStages().map { entities ->
        entities.map { it.toModel() }
    }

    companion object {
        private var instance: FavoriteRecipeRepositoryImpl? = null
        fun getInstance(app: Application) = instance ?: FavoriteRecipeRepositoryImpl(
            dao = AppDb.getInstance(
                context = app
            ).postDao
        ).also { instance = it }
    }
}