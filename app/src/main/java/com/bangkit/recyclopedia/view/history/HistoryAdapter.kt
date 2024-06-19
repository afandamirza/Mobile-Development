package com.bangkit.recyclopedia.view.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.recyclopedia.databinding.ItemRecyclerviewHistoryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class History(
    val description: String,
    val date: Date,
    val status: Status
)

enum class Status {
    DONE,
    ON_PROGRESS
}

class HistoryAdapter : ListAdapter<History, HistoryAdapter.ViewHolder>(
    HistoryDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRecyclerviewHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemRecyclerviewHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            binding.tvDescription.text = history.description

            val doneDateFormat = SimpleDateFormat("MMMM dd, yyyy hh:mm:ss", Locale.forLanguageTag("id-ID"))
            val onProgressDateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.forLanguageTag("id-ID"))
            binding.tvDate.text = if (history.status == Status.DONE) {
                doneDateFormat.format(history.date)
            } else {
                onProgressDateFormat.format(history.date)
            }

            binding.tvPoint.text = if (history.status == Status.DONE) {
                "+Rp 100.00"
            } else {
                "Continue >"
            }
        }
    }

    class HistoryDiffCallback : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }
    }
}