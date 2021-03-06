package ru.netology.nmedia.viewModel

import android.app.Application
import ru.netology.nmedia.repo.RecipeRepository
import ru.netology.nmedia.repo.impl.RecipeRepositoryImpl

class StartViewModel(
    application: Application
) : AbstractRecipeViewModel(application) {
    override val repository: RecipeRepository =
        RecipeRepositoryImpl.getInstance(application)

    fun onAddClicked() {
        navigateToRecipeContentScreen.call()
    }
}