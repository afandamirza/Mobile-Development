package com.bangkit.recyclopedia.view.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bangkit.recyclopedia.databinding.ItemRecycleBinding

data class RecycleItem(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
)

class RecycleListAdapter : ListAdapter<RecycleItem, RecycleListAdapter.ViewHolder>(
    RecycleItemDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecycleListAdapter.ViewHolder {
        val binding = ItemRecycleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecycleListAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class RecycleItemDiffCallback : DiffUtil.ItemCallback<RecycleItem>() {
        override fun areItemsTheSame(oldItem: RecycleItem, newItem: RecycleItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RecycleItem, newItem: RecycleItem): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(private val binding: ItemRecycleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecycleItem) {
            binding.apply {
                ivRecycleItem.load(item.image) {
                    crossfade(true)
                }
                tvItemName.text = item.title
                tvItemDescription.text = item.description
                cardView.setCardBackgroundColor(if (absoluteAdapterPosition % 2 == 0) Color.parseColor("#198F24") else Color.parseColor("#1D9738"))
            }
        }
    }
}