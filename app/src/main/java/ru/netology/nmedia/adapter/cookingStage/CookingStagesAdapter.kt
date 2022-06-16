package ru.netology.nmedia.adapter.cookingStage

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.data.CookingStage
import ru.netology.nmedia.databinding.PointRecipeBinding
import java.io.File

internal class CookingStagesAdapter(
    private val description: List<CookingStage>
) : RecyclerView.Adapter<CookingStagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PointRecipeBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(description[position])
    }

    override fun getItemCount() = description.size

    inner class ViewHolder(
        private val binding: PointRecipeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var cookingStage: CookingStage

        fun bind(cookingStage: CookingStage) {
            this.cookingStage = cookingStage
            with(binding) {
                preparation.text = cookingStage.name
                imagePreview.visibility = View.GONE
                cookingStage.pathImage?.let {
                    imagePreview.visibility = View.VISIBLE
                    imagePreview.setImageURI(Uri.parse(it))
                }
            }
        }
    }
}