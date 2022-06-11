package ru.netology.nmedia.ui

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import ru.netology.nmedia.viewModel.StartViewModel

class StartRecipeFragment : AbstractRecipeFragment() {

    override val viewModel by activityViewModels<StartViewModel>()

    companion object {
        @JvmStatic
        fun newInstance() = StartRecipeFragment()
    }
}