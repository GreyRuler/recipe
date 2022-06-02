package ru.netology.nmedia.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CookingStage(
    val id: Long,
    val recipeId: Long,
    val name: String
) : Parcelable
