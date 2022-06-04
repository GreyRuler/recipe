package ru.netology.nmedia.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cookingStages",
    foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CookingStageEntity(
    @PrimaryKey
    val id: Long,
    val recipeId: Long,
    val name: String
)