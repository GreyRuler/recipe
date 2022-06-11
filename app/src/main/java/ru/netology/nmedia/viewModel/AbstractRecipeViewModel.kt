package ru.netology.nmedia.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.adapter.RecipeInteractionListener
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.data.Recipe
import ru.netology.nmedia.data.RecipeWithCookingStages
import ru.netology.nmedia.repo.RecipeRepository
import ru.netology.nmedia.util.SingleLiveEvent
import java.util.*

abstract class AbstractRecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeInteractionListener {

    abstract val repository: RecipeRepository

    val data by lazy {
        repository::data.get()
    }

    val filteredData = SingleLiveEvent<List<RecipeWithCookingStages>>()
    val navigateToRecipeContentScreen = SingleLiveEvent<RecipeWithCookingStages>()
    val navigateToRecipeScreen = SingleLiveEvent<RecipeWithCookingStages>()
    private val currentRecipeWithCookingStages =
        MutableLiveData<RecipeWithCookingStages?>(null)

    fun onSaveButtonClicked(
        nameRecipe: String,
        category: String,
        cookingStages: List<CookingStage>
    ) {
        if (nameRecipe.isBlank()) return
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

    // region PostInteractionListener

    override fun onRemoveClicked(recipeWithCookingStages: RecipeWithCookingStages) {
        repository.delete(recipeWithCookingStages.recipe.id)
    }

    override fun onEditClicked(recipeWithCookingStages: RecipeWithCookingStages) {
        currentRecipeWithCookingStages.value = recipeWithCookingStages
        navigateToRecipeContentScreen.value = recipeWithCookingStages
    }

    override fun onRecipeWithCookingStagesClicked(
        recipeWithCookingStages: RecipeWithCookingStages
    ) {
        navigateToRecipeScreen.value = recipeWithCookingStages
    }

    override fun onFilterClicked(filterCategories: List<String>) {
        filteredData.value = repository.filter(filterCategories)
    }

    override fun onSearchClicked(text: String?) {
        filteredData.value = repository.search(text)
    }

    override fun onFavoriteClicked(recipeWithCookingStages: RecipeWithCookingStages) {
        repository.like(recipeWithCookingStages.recipe.id)
    }

    // endregion PostInteractionListener
}