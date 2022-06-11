package ru.netology.nmedia.viewModel

import android.app.Application
import ru.netology.nmedia.repo.RecipeRepository
import ru.netology.nmedia.repo.impl.FavoriteRecipeRepositoryImpl

class FavoriteViewModel (
    application: Application
) : AbstractRecipeViewModel(application) {
    override val repository: RecipeRepository =
        FavoriteRecipeRepositoryImpl.getInstance(application)
}