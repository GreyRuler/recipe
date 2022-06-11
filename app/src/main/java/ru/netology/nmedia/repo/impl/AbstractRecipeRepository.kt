package ru.netology.nmedia.repo.impl

import ru.netology.nmedia.data.RecipeWithCookingStages
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.convertion.toEntity
import ru.netology.nmedia.repo.RecipeRepository

abstract class AbstractRecipeRepository(
    private val dao: PostDao
) : RecipeRepository {

    override fun save(recipeWithCookingStages: RecipeWithCookingStages) {
        dao.insert(recipeWithCookingStages.toEntity())
    }

    override fun search(text: String?) =
        if (!text.isNullOrBlank()) {
            data.value?.filter { recipeWithCookingStages ->
                recipeWithCookingStages.recipe.nameRecipe
                    .lowercase().contains(text.lowercase())
            }
        } else data.value

    override fun filter(filterCategories: List<String>) =
        if (filterCategories.isNotEmpty()) {
            data.value?.filter { recipeWithCookingStages ->
                recipeWithCookingStages.recipe.category in filterCategories
            }
        } else data.value

    override fun like(recipeId: Long) = dao.likeById(recipeId)

    override fun delete(recipeId: Long) = dao.removeById(recipeId)
}