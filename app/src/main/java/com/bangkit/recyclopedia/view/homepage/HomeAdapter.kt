package com.bangkit.recyclopedia.view.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.recyclopedia.databinding.ItemRecyclerviewHomeBinding

class HomeAdapter (private val items: List<MyItem>) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRecyclerviewHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    class MyViewHolder(private val binding: ItemRecyclerviewHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyItem) {
            binding.imageView.setImageResource(item.imageResId)
            binding.titleTextView.text = item.title
            binding.descriptionTextView.text = item.description
        }
    }
}

data class MyItem(val imageResId: Int, val title: String, val description: String)