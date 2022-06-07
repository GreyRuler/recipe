package ru.netology.nmedia.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "nameRecipe")
    val nameRecipe: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean,
)