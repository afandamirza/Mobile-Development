// HistoryAdapter.kt
package com.bangkit.recyclopedia.view.history

import android.annotation.SuppressLint
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.api.response.HistoryItemsResponse
import com.bangkit.recyclopedia.databinding.ItemRecyclerviewHistoryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryAdapter : PagingDataAdapter<HistoryItemsResponse, HistoryAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemRecyclerviewHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = getItem(position)
        if (story != null) {
            holder.bind(story)
        }
    }

    inner class ViewHolder(private val binding: ItemRecyclerviewHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(historyData: HistoryItemsResponse) {
            val result = historyData.result

            val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            val targetFormat = SimpleDateFormat("MMMM dd, yyyy 'Pukul' HH:mm:ss", Locale.US)

            val date: Date? = originalFormat.parse(historyData.createdAt)
            val formattedDate: String = targetFormat.format(date)

            when (result) {
                "cardboard" -> {
                    binding.tvDescription.text = "Kardus"
                    binding.tvDate.text = formattedDate
                    binding.tvPoint.text = "+Rp ${historyData.poin}.00"

                }"glass" -> {
                    binding.tvDescription.text = "Kaca"
                    binding.tvDate.text = formattedDate
                    binding.tvPoint.text = "+Rp ${historyData.poin}.00"

                }"metal" -> {
                    binding.tvDescription.text = "Kardus"
                    binding.tvDate.text = formattedDate
                    binding.tvPoint.text = "+Rp ${historyData.poin}.00"

                }"paper" -> {
                    binding.tvDescription.text = "Kertas"
                    binding.tvDate.text = formattedDate
                    binding.tvPoint.text = "+Rp ${historyData.poin}.00"

                }"plastic" -> {
                    binding.tvDescription.text = "Plastik"
                    binding.tvDate.text = formattedDate
                    binding.tvPoint.text = "+Rp ${historyData.poin}.00"

                }
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryItemsResponse>() {
            override fun areItemsTheSame(oldItem: HistoryItemsResponse, newItem: HistoryItemsResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: HistoryItemsResponse,
                newItem: HistoryItemsResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}