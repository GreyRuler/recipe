package ru.netology.nmedia.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class RecipeWithCookingStagesEntity(
    @Embedded val recipe: RecipeEntity,
    @Relation(
        parentColumn = "id",
        entity = CookingStageEntity::class,
        entityColumn = "recipeId"
    )
    val cookingStages: List<CookingStageEntity>
)
