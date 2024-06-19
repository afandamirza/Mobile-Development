package com.bangkit.recyclopedia.view.classification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.recyclopedia.databinding.ItemRecyclerviewClassificationBinding

class ClassificationResultAdapter : ListAdapter<Int, ClassificationResultAdapter.ViewHolder>(
    TutorialDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRecyclerviewClassificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemRecyclerviewClassificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(@DrawableRes item: Int) {
            binding.imageView.setImageResource(item)
        }
    }

    class TutorialDiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}