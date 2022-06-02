package ru.netology.nmedia.db.convertion

import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.db.entity.CookingStageEntity

internal fun CookingStageEntity.toModel() = CookingStage(
    id = id,
    recipeId = recipeId,
    name = name
)

internal fun CookingStage.toEntity() = CookingStageEntity(
    id = id,
    recipeId = recipeId,
    name = name
)