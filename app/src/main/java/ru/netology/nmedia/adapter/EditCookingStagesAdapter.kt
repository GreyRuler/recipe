package ru.netology.nmedia.adapter

import android.opengl.Visibility
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
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
        when(getItemViewType(position)) {
            HEAD -> holder.bind(getItem(position), true)
            LIST -> holder.bind(getItem(position), false)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) HEAD else LIST
    }

    override fun getItemCount() = currentList.size

    class ViewHolder(
        private val binding: EditDescriptionBinding,
        listener: CookingStageInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var cookingStage: CookingStage

        init {
            with(binding) {
                editPreparation.addTextChangedListener {
                    cookingStage.name = it.toString()
                }
                close.setOnClickListener {
                    listener.onRemoveClicked(cookingStage.id)
                }
                add.setOnClickListener {
                    listener.onAddClicked(cookingStage.recipeId)
                }
            }
        }

        fun bind(cookingStage: CookingStage, visibility: Boolean) {
            this.cookingStage = cookingStage
            with(binding) {
                editPreparation.setText(cookingStage.name)
                if (visibility) {
                    add.visibility = View.VISIBLE
                    close.visibility = View.GONE
                } else {
                    add.visibility = View.GONE
                    close.visibility = View.VISIBLE
                }
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<CookingStage>() {

        override fun areItemsTheSame(oldItem: CookingStage, newItem: CookingStage) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CookingStage, newItem: CookingStage) =
            oldItem == newItem
    }

    companion object {
        const val HEAD = 1
        const val LIST = 0
    }
}