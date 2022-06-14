package ru.netology.nmedia.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe (
    val id: Long,
    val author: String,
    val nameRecipe: String,
    val category: String,
    val favorite: Boolean = false
): Parcelable
