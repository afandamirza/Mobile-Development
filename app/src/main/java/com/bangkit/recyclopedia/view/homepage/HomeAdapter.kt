package com.bangkit.recyclopedia.view.homepage

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bangkit.recyclopedia.databinding.ItemRecyclerviewHomeBinding


class HomeAdapter : ListAdapter<RecycleItem, HomeAdapter.ViewHolder>(
    RecycleItemDiffCallback()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeAdapter.ViewHolder {
        val binding = ItemRecyclerviewHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
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

    inner class ViewHolder(private val binding: ItemRecyclerviewHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    val intent = Intent(binding.root.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_RECYCLE_ITEM, item)
                    binding.root.context.startActivity(intent)
                }
            }
        }

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
