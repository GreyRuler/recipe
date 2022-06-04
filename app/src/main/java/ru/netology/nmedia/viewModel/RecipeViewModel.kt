package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Recipe
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.data.RecipeWithCookingStages
import ru.netology.nmedia.repo.RecipeRepository
import ru.netology.nmedia.repo.impl.RecipeRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {

    private val repository: RecipeRepository =
        RecipeRepositoryImpl.getInstance(application)

    val data by repository::data

    val navigateToRecipeContentScreen = SingleLiveEvent<RecipeWithCookingStages>()
    val backRemovedListener = SingleLiveEvent<RecipeWithCookingStages>()
    private val currentRecipeWithCookingStages = MutableLiveData<RecipeWithCookingStages?>(null)

    fun onSaveButtonClicked(
        nameRecipe: String,
        categories: String,
        cookingStages: List<CookingStage>
    ) {
        if (nameRecipe.isBlank()) return
        val recipeWithCookingStages = currentRecipeWithCookingStages.value?.copy(
            recipe = currentRecipeWithCookingStages.value!!.recipe.copy(
                nameRecipe = nameRecipe
            ),
            cookingStages = cookingStages
        ) ?: RecipeWithCookingStages(
            recipe = Recipe(
                id = RecipeRepository.NEW_RECIPE_ID,
                author = "Me",
                nameRecipe = nameRecipe,
                category = categories
            ),
            cookingStages = cookingStages
        )
        repository.save(recipeWithCookingStages)
        currentRecipeWithCookingStages.value = null
    }

    // region PostInteractionListener

    override fun onRemoveClicked(recipeWithCookingStages: RecipeWithCookingStages) {
        repository.delete(recipeWithCookingStages.recipe.id)
        backRemovedListener.call()
    }

    override fun onEditClicked(recipeWithCookingStages: RecipeWithCookingStages) {
        currentRecipeWithCookingStages.value = recipeWithCookingStages
        navigateToRecipeContentScreen.value = recipeWithCookingStages
    }

    override fun onFavoriteClicked(recipeWithCookingStages: RecipeWithCookingStages) {
        TODO("Not yet implemented")
    }

    override fun onRecipeWithCookingStagesClicked(recipeWithCookingStages: RecipeWithCookingStages) {
    }

    // endregion PostInteractionListener
}