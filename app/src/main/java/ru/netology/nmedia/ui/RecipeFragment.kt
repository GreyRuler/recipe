package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.RecipesAdapter
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.databinding.RecipeFragmentBinding
import ru.netology.nmedia.viewModel.RecipeViewModel

class RecipeFragment : Fragment() {

    private val args by navArgs<RecipeFragmentArgs>()
    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(
            requestKey = RecipeContentFragment.REQUEST_KEY_RECIPE_FRAGMENT
        ) { requestKey, bundle ->
            if (requestKey != RecipeContentFragment.REQUEST_KEY_START_FRAGMENT) return@setFragmentResultListener
            val nameRecipe = bundle.getString(
                RecipeContentFragment.NAME_RECIPE_KEY
            ) ?: return@setFragmentResultListener
            val categories = bundle.getString(
                RecipeContentFragment.CATEGORIES_KEY
            ) ?: return@setFragmentResultListener
            val cookingStages = bundle.getParcelableArrayList<CookingStage>(
                RecipeContentFragment.COOKING_STAGES_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(nameRecipe, categories, cookingStages)
        }

        viewModel.navigateToRecipeContentScreen.observe(this) { recipe ->
            val direction = RecipeFragmentDirections
                .toRecipeContentFragment(recipe,"requestKeyRecipeFragment")
            findNavController().navigate(direction)
        }

        viewModel.backRemovedListener.observe(this) {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeFragmentBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
        viewModel.data.observe(viewLifecycleOwner) { posts->
            val currentPost = posts.find { recipeWithCookingStages ->
                recipeWithCookingStages.recipe.id == args.recipeWithCookingStages.recipe.id
            }
            val holder = RecipesAdapter.ViewHolder(binding, viewModel)
            if (currentPost != null) holder.bind(currentPost)
        }
    }.root
}