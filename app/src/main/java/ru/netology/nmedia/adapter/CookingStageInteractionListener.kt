package ru.netology.nmedia.adapter

import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.data.Recipe

interface CookingStageInteractionListener {

    fun onAddClicked(recipeId: Long, cookingStage: CookingStage)
    fun onRemoveClicked(recipeId: Long, cookingStageId: Long)
}