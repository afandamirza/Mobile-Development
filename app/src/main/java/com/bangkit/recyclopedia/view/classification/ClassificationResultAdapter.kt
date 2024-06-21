package com.bangkit.recyclopedia.view.classification

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.recyclopedia.databinding.ItemRecyclerviewClassificationBinding

class ClassificationResultAdapter(
    private val context: Context
) : ListAdapter<Int, ClassificationResultAdapter.ViewHolder>(
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
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            // Determine the YouTube link based on the item position
            val videoUrl = getYouTubeVideoUrl(position)
            // Launch the YouTube video using an Intent
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl)))
        }
    }

    private fun getYouTubeVideoUrl(position: Int): String {
        return when (position) {
            0 -> "https://www.youtube.com/watch?v=lOXCHuJVa_c"
            1 -> "https://www.youtube.com/watch?v=_G2IitNrgIw"
            2 -> "https://www.youtube.com/watch?v=l_qM7nRLEcw"
            3 -> "https://www.youtube.com/watch?v=_J4GzEzSNqw"
            else -> "https://www.youtube.com"
        }
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