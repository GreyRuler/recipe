package ru.netology.nmedia.viewModel

import android.content.Context
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.cookingStage.CookingStageInteractionListener
import ru.netology.nmedia.adapter.cookingStage.helper.ItemTouchHelperAdapter
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.repo.CookingStageRepository
import ru.netology.nmedia.repo.RecipeRepository
import ru.netology.nmedia.repo.impl.CookingStageRepositoryImpl
import ru.netology.nmedia.util.SingleLiveEvent

class CookingStageViewModel(
    cookingStages: List<CookingStage>?,
    context: Context,
    private val selectImageFromGalleryResult: ActivityResultLauncher<String>
) : ViewModel(), CookingStageInteractionListener {

    private var nextCookingStageId = AppDb.getInstance(
        context = context
    ).postDao.getMaxIdCookingStage()

    private val currentCookingStageId = SingleLiveEvent<Long>()

    private val repository: CookingStageRepository =
        cookingStages?.let {
            CookingStageRepositoryImpl(it)
        } ?: CookingStageRepositoryImpl(
            listOf(
                CookingStage(
                    id = ++nextCookingStageId,
                    recipeId = RecipeRepository.NEW_RECIPE_ID,
                    name = "",
                    pathImage = null
                )
            )
        )

    val data by repository::data

    override fun onAttachImageClicked(cookingStageId: Long) {
        currentCookingStageId.value = cookingStageId
        selectImageFromGalleryResult.launch("image/*")
    }

    override fun onDeleteImageClicked(cookingStageId: Long) {
        repository.deleteImage(cookingStageId)
    }

    override fun onSelectImageClicked(uri: Uri, context: Context) {
        repository.selectImage(uri, context, currentCookingStageId.value!!)
    }

    override fun onAddClicked(recipeId: Long) {
        repository.addCookingStage(++nextCookingStageId)
    }

    override fun onRemoveClicked(cookingStageId: Long) {
        repository.deleteCookingStage(cookingStageId)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val prev = (data.value as MutableList).removeAt(fromPosition)
        (data.value as MutableList).add(
            if (toPosition > fromPosition) toPosition - 1 else toPosition,
            prev
        )
    }

    override fun onItemDismiss(position: Int) {
        (data.value as MutableList).removeAt(position)
    }

}