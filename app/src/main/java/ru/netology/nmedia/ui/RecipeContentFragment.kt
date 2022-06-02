package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.adapter.EditCookingStagesAdapter
import ru.netology.nmedia.databinding.RecipeContentFragmentBinding
import ru.netology.nmedia.viewModel.CookingStageViewModel

class RecipeContentFragment : Fragment() {

    private val args by navArgs<RecipeContentFragmentArgs>()
    private val viewModel by viewModels<CookingStageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeContentFragmentBinding.inflate(
        layoutInflater, container, false
    ).apply {
        editNameRecipe.setText(args.recipeWithCookingStages?.recipe?.nameRecipe)
        editCategories.setText(args.recipeWithCookingStages?.recipe?.category)
        editCookingStageRecyclerView.adapter = EditCookingStagesAdapter(viewModel)
        editNameRecipe.requestFocus()
        ok.setOnClickListener {
            onOkButtonClicked(this)
        }
    }.root

    private fun onOkButtonClicked(binding: RecipeContentFragmentBinding) {
        val nameRecipe = binding.editNameRecipe.text
        val categories = binding.editCategories.text
        val cookingStages = viewModel.data.value
        if (!nameRecipe.isNullOrBlank() &&
            !cookingStages.isNullOrEmpty() &&
            !categories.isNullOrBlank()
        ) {
            val resultBundle = Bundle(3)
            resultBundle.putString(NAME_RECIPE_KEY, nameRecipe.toString())
            resultBundle.putSerializable(CATEGORIES_KEY, categories.toString())
            resultBundle.putParcelableArrayList(COOKING_STAGES_KEY, ArrayList(cookingStages))
            setFragmentResult(args.requestKey, resultBundle)
        }
        findNavController().popBackStack()
    }

    companion object {
        const val REQUEST_KEY_START_FRAGMENT = "requestKeyStartFragment"
        const val REQUEST_KEY_POST_FRAGMENT = "requestKeyPostFragment"
        const val NAME_RECIPE_KEY = "nemNameRecipe"
        const val COOKING_STAGES_KEY = "newCookingStages"
        const val CATEGORIES_KEY = "newCategories"
    }
}