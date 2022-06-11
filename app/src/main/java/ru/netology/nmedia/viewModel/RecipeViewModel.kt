package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.data.Recipe
import ru.netology.nmedia.adapter.RecipeInteractionListener
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.data.RecipeWithCookingStages
import ru.netology.nmedia.repo.RecipeRepository
import ru.netology.nmedia.repo.impl.AbstractRecipeRepository
import ru.netology.nmedia.repo.impl.RecipeRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent

class RecipeViewModel(
    application: Application
) : AbstractRecipeViewModel(application) {

    override val repository: RecipeRepository =
        RecipeRepositoryImpl.getInstance(application)

    val backRemovedListener = SingleLiveEvent<RecipeWithCookingStages>()

    override fun onRemoveClicked(recipeWithCookingStages: RecipeWithCookingStages) {
        super.onRemoveClicked(recipeWithCookingStages)
        backRemovedListener.call()
    }
}