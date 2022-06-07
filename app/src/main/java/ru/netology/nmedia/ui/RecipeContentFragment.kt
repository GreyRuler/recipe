package ru.netology.nmedia.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import ru.netology.nmedia.adapter.EditCookingStagesAdapter
import ru.netology.nmedia.databinding.RecipeContentFragmentBinding
import ru.netology.nmedia.util.viewModelsFactory
import ru.netology.nmedia.viewModel.CookingStageViewModel

class RecipeContentFragment : Fragment() {

    private val args by navArgs<RecipeContentFragmentArgs>()
    private val viewModel by viewModelsFactory {
        CookingStageViewModel(
            args.recipeWithCookingStages?.cookingStages,
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeContentFragmentBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
        binding.editNameRecipe.setText(args.recipeWithCookingStages?.recipe?.nameRecipe)
        binding.categoriesChipGroup.forEach {
            if ((it as Chip).text == args.recipeWithCookingStages?.recipe?.category) it.isChecked = true
        }
        val adapter = EditCookingStagesAdapter(viewModel)
        binding.editCookingStageRecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { cookingStages ->
            adapter.submitList(cookingStages)
        }
        binding.editNameRecipe.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }
    }.root

    private fun onOkButtonClicked(binding: RecipeContentFragmentBinding) {
        val nameRecipe = binding.editNameRecipe.text.toString()
        val category = binding.categoriesChipGroup.findViewById<Chip>(
            binding.categoriesChipGroup.checkedChipId
        ).text.toString()
        Log.d("category", category)
        val cookingStages = viewModel.data.value?.filter { it.name.isNotBlank() }
        if (nameRecipe.isNotBlank() &&
            !cookingStages.isNullOrEmpty() &&
            category.isNotBlank()
        ) {
            val resultBundle = Bundle(3)
            resultBundle.putString(NAME_RECIPE_KEY, nameRecipe)
            resultBundle.putSerializable(CATEGORY_KEY, category)
            resultBundle.putParcelableArrayList(COOKING_STAGES_KEY, ArrayList(cookingStages))
            setFragmentResult(args.requestKey, resultBundle)
        }
        findNavController().popBackStack()
    }

    companion object {
        const val REQUEST_KEY_START_FRAGMENT = "requestKeyStartFragment"
        const val REQUEST_KEY_RECIPE_FRAGMENT = "requestKeyRecipeFragment"
        const val NAME_RECIPE_KEY = "nemNameRecipe"
        const val COOKING_STAGES_KEY = "newCookingStages"
        const val CATEGORY_KEY = "newCategory"
    }
}