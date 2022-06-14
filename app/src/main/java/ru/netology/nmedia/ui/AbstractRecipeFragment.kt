package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import ru.netology.nmedia.adapter.RecipesAdapter
import ru.netology.nmedia.databinding.StartFragmentBinding
import ru.netology.nmedia.viewModel.AbstractRecipeViewModel


abstract class AbstractRecipeFragment : Fragment() {

    abstract val viewModel: AbstractRecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigateToRecipeContentScreen.observe(this) { recipeWithCookingStages ->
            val direction = AbstractRecipeFragmentDirections.toRecipeContentFragment(
                recipeWithCookingStages,
                RecipeContentFragment.REQUEST_KEY_MAIN_FRAGMENT
            )
            findNavController().navigate(direction)
        }

        viewModel.navigateToRecipeScreen.observe(this) { recipe ->
            val direction = AbstractRecipeFragmentDirections.toRecipeFragment(recipe)
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
        viewModel.filteredData.observe(viewLifecycleOwner) { recipeWithCookingStages ->
            adapter.submitList(recipeWithCookingStages)
        }
        binding.searchBar.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.onSearchClicked(query)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.onSearchClicked(newText)
                    return false
                }
            }
        )
        binding.categoriesChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            viewModel.onFilterClicked(
                checkedIds.map {
                    group.findViewById<Chip>(it).text.toString()
                }
            )
        }
    }.root


}
