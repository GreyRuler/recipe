package ru.netology.nmedia.repo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.CookingStage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

interface CookingStageRepository {

    val data: MutableLiveData<List<CookingStage>>

    fun addCookingStage(nextCookingStageId: Long)
    fun deleteCookingStage(cookingStageId: Long)
    fun saveImage(uri: Uri, context: Context): String
    fun deleteImage(cookingStageId: Long)
    fun selectImage(uri: Uri, context: Context, cookingStageId: Long)
}
