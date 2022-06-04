package ru.netology.nmedia.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal fun EditText.hideKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, /*flags =*/0)
}

internal fun EditText.showKeyBoard() {
    this.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
//    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

inline fun <reified T : ViewModel> Fragment.viewModelsFactory(crossinline viewModelInitialization: () -> T): Lazy<T> {
    return viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return viewModelInitialization.invoke() as T
            }
        }
    }
}