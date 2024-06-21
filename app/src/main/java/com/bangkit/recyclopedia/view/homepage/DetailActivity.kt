package com.bangkit.recyclopedia.view.homepage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.bangkit.recyclopedia.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding


    companion object {
        const val EXTRA_RECYCLE_ITEM = "extra_recycle_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<RecycleItem>(EXTRA_RECYCLE_ITEM)
        item?.let { displayItemDetails(it) }
    }

    private fun displayItemDetails(item: RecycleItem) {
        binding.apply {
            ivItemDetail.load(item.image) {
                crossfade(true)
            }
            tvItemNameDetail.text = item.title
            tvItemDescriptionDetail.text = item.description
        }

    }


}