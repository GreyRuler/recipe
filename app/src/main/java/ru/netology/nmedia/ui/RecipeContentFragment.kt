package ru.netology.nmedia.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import ru.netology.nmedia.adapter.EditCookingStagesAdapter
import ru.netology.nmedia.databinding.RecipeContentFragmentBinding
import ru.netology.nmedia.util.viewModelsFactory
import ru.netology.nmedia.viewModel.CookingStageViewModel

class RecipeContentFragment : Fragment() {

    private val args by navArgs<RecipeContentFragmentArgs>()
    val viewModel by viewModelsFactory {
        CookingStageViewModel(
            args.recipeWithCookingStages?.cookingStages,
            requireContext(),
            selectImageFromGalleryResult
        )
    }

    private val selectImageFromGalleryResult: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                viewModel.onSelectImageClicked(uri, requireContext())
            }
        }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeContentFragmentBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
        binding.editNameRecipe.setText(args.recipeWithCookingStages?.recipe?.nameRecipe)
        binding.categoriesChipGroup.forEach {
            if ((it as Chip).text == args.recipeWithCookingStages?.recipe?.category)
                it.isChecked = true
        }
        val adapter = EditCookingStagesAdapter(viewModel)
        binding.editCookingStageRecyclerView.adapter = adapter

        adapter.registerAdapterDataObserver(
            object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(
                    positionStart: Int,
                    itemCount: Int
                ) {
                    binding.editCookingStageRecyclerView
                        .smoothScrollToPosition(itemCount - 1)
                }

                override fun onItemRangeRemoved(
                    positionStart: Int,
                    itemCount: Int
                ) {
                    binding.editCookingStageRecyclerView
                        .smoothScrollToPosition(itemCount)
                }
            }
        )

        viewModel.data.observe(viewLifecycleOwner) { cookingStages ->
//            adapter.submitList(null)
            adapter.submitList(cookingStages)
            adapter.notifyDataSetChanged()
        }
        binding.editNameRecipe.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }
    }.root

    private fun onOkButtonClicked(binding: RecipeContentFragmentBinding) {
        val nameRecipe = binding.editNameRecipe.text
        val category = binding.categoriesChipGroup.findViewById<Chip>(
            binding.categoriesChipGroup.checkedChipId
        )?.text
        val cookingStages = viewModel.data.value?.filter { it.name.isNotBlank() }
        if (!nameRecipe.isNullOrBlank() &&
            !cookingStages.isNullOrEmpty() &&
            !category.isNullOrBlank()
        ) {
            val resultBundle = Bundle(3)
            resultBundle.putString(NAME_RECIPE_KEY, nameRecipe.toString())
            resultBundle.putSerializable(CATEGORY_KEY, category.toString())
            resultBundle.putParcelableArrayList(
                COOKING_STAGES_KEY,
                ArrayList(cookingStages)
            )
            setFragmentResult(args.requestKey, resultBundle)
        }
        findNavController().popBackStack()
    }

    companion object {
        const val REQUEST_KEY_MAIN_FRAGMENT = "requestKeyMainFragment"
        const val REQUEST_KEY_RECIPE_FRAGMENT = "requestKeyRecipeFragment"
        const val NAME_RECIPE_KEY = "nemNameRecipe"
        const val COOKING_STAGES_KEY = "newCookingStages"
        const val CATEGORY_KEY = "newCategory"
    }
}