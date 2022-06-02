package ru.netology.nmedia.db.convertion

import ru.netology.nmedia.data.RecipeWithCookingStages
import ru.netology.nmedia.db.convertion.toModel
import ru.netology.nmedia.db.entity.RecipeWithCookingStagesEntity


internal fun RecipeWithCookingStagesEntity.toModel() = RecipeWithCookingStages(
    recipe = recipe.toModel(),
    cookingStages = cookingStages.map { cookingStageEntity ->
        cookingStageEntity.toModel()
    }
)

internal fun RecipeWithCookingStages.toEntity() = RecipeWithCookingStagesEntity(
    recipe = recipe.toEntity(),
    cookingStages = cookingStages.map { cookingStage ->
        cookingStage.toEntity()
    }
)