package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.data.Recipe
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Categories
import ru.netology.nmedia.data.RecipeWithCookingStages
import ru.netology.nmedia.databinding.RecipeFragmentBinding

internal class RecipesAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<RecipeWithCookingStages, RecipesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeFragmentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: RecipeFragmentBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipeWithCookingStages: RecipeWithCookingStages

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_recipe)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(
                                recipeWithCookingStages
                            )
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(
                                recipeWithCookingStages
                            )
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            with(binding) {
//                like.setOnClickListener {
//                    listener.onLikeClicked(post)
//                }
                options.setOnClickListener { popupMenu.show() }
                cardPost.setOnClickListener {
                    listener.onRecipeWithCookingStagesClicked(
                        recipeWithCookingStages
                    )
                }
            }
        }

        fun bind(recipeWithCookingStages: RecipeWithCookingStages) {
            this.recipeWithCookingStages = recipeWithCookingStages
            with(binding) {
                nameRecipe.text = recipeWithCookingStages.recipe.nameRecipe
                author.text = recipeWithCookingStages.recipe.author
//                ingredientsRecyclerView.adapter = CookingStagesAdapter(recipe.ingredients)
                categories.text = Categories.European.title
                cookingStagesRecyclerView.adapter = CookingStagesAdapter(
                    recipeWithCookingStages.cookingStages
                )
//                favorite.isChecked = recipe.favorite
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<RecipeWithCookingStages>() {

        override fun areItemsTheSame(oldItem: RecipeWithCookingStages, newItem: RecipeWithCookingStages) =
            oldItem.recipe.id == newItem.recipe.id

        override fun areContentsTheSame(oldItem: RecipeWithCookingStages, newItem: RecipeWithCookingStages) =
            oldItem == newItem
    }
}