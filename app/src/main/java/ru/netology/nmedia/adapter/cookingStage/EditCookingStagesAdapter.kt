package ru.netology.nmedia.adapter.cookingStage

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.adapter.cookingStage.helper.ItemTouchHelperAdapter
import ru.netology.nmedia.adapter.cookingStage.helper.ItemTouchHelperViewHolder
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.databinding.EditDescriptionBinding


internal class EditCookingStagesAdapter(
    private val interactionListener: CookingStageInteractionListener
) : ListAdapter<CookingStage, EditCookingStagesAdapter.ViewHolder>(DiffCallback),
    ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EditDescriptionBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            HEAD -> holder.bind(getItem(position), true)
            LIST -> holder.bind(getItem(position), false)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) HEAD else LIST
    }

    override fun getItemCount() = currentList.size

//    override fun submitList(list: List<CookingStage>?) {
//        super.submitList(list?.let { ArrayList(it) })
//    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        interactionListener.onItemDismiss(position)
        notifyItemRemoved(position)
    }

    class ViewHolder(
        private val binding: EditDescriptionBinding,
        listener: CookingStageInteractionListener
    ) : RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

        private lateinit var cookingStage: CookingStage

        init {
            with(binding) {
                editPreparation.addTextChangedListener {
                    cookingStage.name = it.toString()
                }
                closeButton.setOnClickListener {
                    listener.onRemoveClicked(cookingStage.id)
                }
                addButton.setOnClickListener {
                    listener.onAddClicked(cookingStage.recipeId)
                }
                attachButton.setOnClickListener {
                    listener.onAttachImageClicked(cookingStage.id)
                }
                closeImageButton.setOnClickListener {
                    listener.onDeleteImageClicked(cookingStage.id)
                }
            }
        }

        fun bind(cookingStage: CookingStage, visibility: Boolean) {
            this.cookingStage = cookingStage
            with(binding) {
                editPreparation.setText(cookingStage.name)
                if (visibility) {
                    addButton.visibility = View.VISIBLE
                    closeButton.visibility = View.GONE
                } else {
                    addButton.visibility = View.GONE
                    closeButton.visibility = View.VISIBLE
                }
                if (cookingStage.pathImage != null) {
                    imagePreview.setImageURI(Uri.parse(cookingStage.pathImage))
                    imagePreview.visibility = View.VISIBLE
                    closeImageButton.visibility = View.VISIBLE
                    attachButton.visibility = View.GONE
                } else {
                    imagePreview.visibility = View.GONE
                    closeImageButton.visibility = View.GONE
                    attachButton.visibility = View.VISIBLE
                }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
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