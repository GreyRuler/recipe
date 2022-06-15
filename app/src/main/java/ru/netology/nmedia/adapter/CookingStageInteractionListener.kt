package ru.netology.nmedia.adapter

import android.content.Context
import android.net.Uri

interface CookingStageInteractionListener {

    fun onSelectImageClicked(uri: Uri, context: Context)
    fun onAddClicked(recipeId: Long)
    fun onRemoveClicked(cookingStageId: Long)
    fun onAttachImageClicked(cookingStageId: Long)
    fun onCloseImageClicked(cookingStageId: Long)
}