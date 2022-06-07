package ru.netology.nmedia.data

import android.opengl.Visibility
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CookingStage(
    val id: Long,
    val recipeId: Long,
    var name: String
) : Parcelable
