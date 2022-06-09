package ru.netology.nmedia.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.adapter.RecipesAdapter
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.databinding.StartFragmentBinding
import ru.netology.nmedia.viewModel.StartViewModel

class StartFragment : Fragment() {

    private val viewModel by activityViewModels<StartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigateToRecipeContentScreen.observe(this) { recipeWithCookingStages ->
            val direction = StartFragmentDirections
                .toRecipeContentFragment(recipeWithCookingStages, RecipeContentFragment.REQUEST_KEY_MAIN_FRAGMENT)
            findNavController().navigate(direction)
        }

        viewModel.navigateToRecipeScreen.observe(this) { recipe ->
            val direction = StartFragmentDirections.toRecipeFragment(recipe)
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = StartFragmentBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
        val adapter = RecipesAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipeWithCookingStages ->
            adapter.submitList(recipeWithCookingStages)
        }
    }.root

    companion object {
        @JvmStatic
        fun newInstance() = StartFragment()
    }
}
