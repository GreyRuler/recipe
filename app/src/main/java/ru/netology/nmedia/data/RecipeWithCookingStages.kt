package ru.netology.nmedia.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class RecipeWithCookingStages(
    val recipe: @RawValue Recipe,
    val cookingStages: @RawValue List<CookingStage>
) : Parcelable
