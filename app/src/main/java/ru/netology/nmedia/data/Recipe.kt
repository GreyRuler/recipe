package ru.netology.nmedia.data

data class Recipe (
    val id: Long,
    val author: String,
    val nameRecipe: String,
    val category: String,
    val favorite: Boolean = false
)
