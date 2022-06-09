package ru.netology.nmedia.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Recipe
import ru.netology.nmedia.adapter.RecipeInteractionListener
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.data.RecipeWithCookingStages
import ru.netology.nmedia.repo.RecipeRepository
import ru.netology.nmedia.repo.impl.RecipeRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent

class StartViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener {

    private val repository: RecipeRepository =
        RecipeRepositoryImpl.getInstance(application)

    val data by repository::data

    val navigateToRecipeContentScreen = SingleLiveEvent<RecipeWithCookingStages>()
    val navigateToRecipeScreen = SingleLiveEvent<RecipeWithCookingStages>()
    private val currentRecipeWithCookingStages = MutableLiveData<RecipeWithCookingStages?>(null)

    fun onSaveButtonClicked(
        nameRecipe: String,
        category: String,
        cookingStages: List<CookingStage>
    ) {
        val recipeWithCookingStages = currentRecipeWithCookingStages.value?.copy(
            recipe = currentRecipeWithCookingStages.value!!.recipe.copy(
                nameRecipe = nameRecipe,
                category = category
            ),
            cookingStages = cookingStages
        ) ?: RecipeWithCookingStages(
            recipe = Recipe(
                id = RecipeRepository.NEW_RECIPE_ID,
                author = "Me",
                nameRecipe = nameRecipe,
                category = category
            ),
            cookingStages = cookingStages
        )
        repository.save(recipeWithCookingStages)
        currentRecipeWithCookingStages.value = null
    }

    fun onAddClicked() {
        navigateToRecipeContentScreen.call()
    }

    // region PostInteractionListener

    override fun onRemoveClicked(recipeWithCookingStages: RecipeWithCookingStages) {
        repository.delete(recipeWithCookingStages.recipe.id)
    }

    override fun onEditClicked(recipeWithCookingStages: RecipeWithCookingStages) {
        currentRecipeWithCookingStages.value = recipeWithCookingStages
        navigateToRecipeContentScreen.value = recipeWithCookingStages
    }

    override fun onFavoriteClicked(recipeWithCookingStages: RecipeWithCookingStages) {
        repository.like(recipeWithCookingStages.recipe.id)
    }

    override fun onRecipeWithCookingStagesClicked(recipeWithCookingStages: RecipeWithCookingStages) {
        navigateToRecipeScreen.value = recipeWithCookingStages
    }

    // endregion PostInteractionListener
}