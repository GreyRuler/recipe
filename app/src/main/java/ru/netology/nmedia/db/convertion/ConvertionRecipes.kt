package ru.netology.nmedia.db.convertion

import ru.netology.nmedia.data.Recipe
import ru.netology.nmedia.db.entity.RecipeEntity

internal fun RecipeEntity.toModel() = Recipe(
    id = id,
    author = author,
    nameRecipe = nameRecipe,
    category = category,
    favorite = favorite
)

internal fun Recipe.toEntity() = RecipeEntity(
    id = id,
    author = author,
    nameRecipe = nameRecipe,
    category = category,
    favorite = favorite
)