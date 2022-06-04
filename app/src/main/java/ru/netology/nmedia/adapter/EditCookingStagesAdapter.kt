package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.databinding.EditDescriptionBinding

internal class EditCookingStagesAdapter(
    private val interactionListener: CookingStageInteractionListener
) : ListAdapter<CookingStage, EditCookingStagesAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EditDescriptionBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: EditDescriptionBinding,
        listener: CookingStageInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var cookingStage: CookingStage

        init {
            with(binding) {
                editPreparation.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        cookingStage.name = editPreparation.text.toString()
                    }
                }
                close.setOnClickListener {
                    close.visibility = View.GONE
                    add.visibility = View.VISIBLE
                    listener.onRemoveClicked(cookingStage.id)
                }
                add.setOnClickListener {
                    close.visibility = View.VISIBLE
                    add.visibility = View.GONE
                    listener.onAddClicked(cookingStage.recipeId)
                }
            }
        }

        fun bind(cookingStage: CookingStage) {
            this.cookingStage = cookingStage
            with(binding) {
                editPreparation.setText(cookingStage.name)
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<CookingStage>() {

        override fun areItemsTheSame(oldItem: CookingStage, newItem: CookingStage) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CookingStage, newItem: CookingStage) =
            oldItem == newItem
    }
}