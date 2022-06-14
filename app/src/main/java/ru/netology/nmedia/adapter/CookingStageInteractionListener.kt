package ru.netology.nmedia.adapter

import android.content.Context
import android.net.Uri

interface CookingStageInteractionListener {

    fun onSelectImageClicked(uri: Uri, context: Context)
    fun onAddClicked(recipeId: Long)
    fun onRemoveClicked(cookingStageId: Long)
    fun onAttachClicked(cookingStageId: Long)
    fun onSaveImage(uri: Uri, context: Context): String
}