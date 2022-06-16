package ru.netology.nmedia.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import ru.netology.nmedia.adapter.AppViewPagerAdapter
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.databinding.MainFragmentBinding
import ru.netology.nmedia.viewModel.StartViewModel


class MainFragment : Fragment() {

    private val viewModel by activityViewModels<StartViewModel>()

    private val fragments: List<AbstractRecipeFragment> = listOf(
        StartRecipeFragment.newInstance(),
        FavoriteRecipeFragment.newInstance()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener(
            requestKey = RecipeContentFragment.REQUEST_KEY_MAIN_FRAGMENT
        ) { requestKey, bundle ->
            if (requestKey != RecipeContentFragment.REQUEST_KEY_MAIN_FRAGMENT)
                return@setFragmentResultListener
            val nameRecipe = bundle.getString(
                RecipeContentFragment.NAME_RECIPE_KEY
            ) ?: return@setFragmentResultListener
            val category = bundle.getString(
                RecipeContentFragment.CATEGORY_KEY
            ) ?: return@setFragmentResultListener
            val cookingStages = bundle.getParcelableArrayList<CookingStage>(
                RecipeContentFragment.COOKING_STAGES_KEY
            ) ?: return@setFragmentResultListener
            viewModel.onSaveButtonClicked(nameRecipe, category, cookingStages)
        }

        viewModel.navigateToRecipeContentScreen.observe(this) { recipeWithCookingStages ->
            val direction = MainFragmentDirections.toRecipeContentFragment(
                recipeWithCookingStages,
                RecipeContentFragment.REQUEST_KEY_MAIN_FRAGMENT
            )
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = MainFragmentBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
        val adapter = AppViewPagerAdapter(
            childFragmentManager,
            lifecycle,
            fragments
        )
        binding.viewPagerApp.adapter = adapter
        TabLayoutMediator(binding.tabLayoutApp, binding.viewPagerApp) { tab, position ->
            when (position) {
                0 -> tab.text = "Главная"
                1 -> tab.text = "Избранное"
            }
        }.attach()
        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }
    }.root
}