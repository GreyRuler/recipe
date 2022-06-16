package ru.netology.nmedia.ui

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import ru.netology.nmedia.viewModel.FavoriteViewModel

class FavoriteRecipeFragment : AbstractRecipeFragment() {

    override val viewModel by activityViewModels<FavoriteViewModel>()

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteRecipeFragment()
    }
}