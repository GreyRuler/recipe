package ru.netology.nmedia.repo.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.repo.CookingStageRepository
import ru.netology.nmedia.repo.RecipeRepository
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CookingStageRepositoryImpl(
    cookingStages: List<CookingStage>
) : CookingStageRepository {

    private val cookingStages
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data: MutableLiveData<List<CookingStage>> =
        MutableLiveData(cookingStages)

    override fun addCookingStage(nextCookingStageId: Long) {
        data.value = cookingStages + listOf(
            CookingStage(
                id = nextCookingStageId,
                recipeId = RecipeRepository.NEW_RECIPE_ID,
                name = "",
                pathImage = null
            )
        )
    }

    override fun deleteCookingStage(cookingStageId: Long) {
        data.value = if (cookingStages.size > 1) cookingStages.filterNot {
            it.id == cookingStageId
        } else return
    }

    override fun saveImage(uri: Uri, context: Context): String {
        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        }
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(
            Date()
        )
        val storageDir = context.filesDir
        val tmpFile = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
        val fileOutputStream = FileOutputStream(tmpFile)
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return tmpFile.path
    }

    override fun deleteImage(cookingStageId: Long) {
        data.value = cookingStages.also { cookingStages ->
            cookingStages.find { it.id == cookingStageId }?.also { cookingStage ->
                cookingStage.pathImage?.let { File(it).delete() }
                cookingStage.pathImage = null
            }
        }
    }

    override fun selectImage(uri: Uri, context: Context, cookingStageId: Long) {
        data.value = cookingStages.apply {
            find { it.id == cookingStageId }?.pathImage = saveImage(uri, context)
        }
    }

}