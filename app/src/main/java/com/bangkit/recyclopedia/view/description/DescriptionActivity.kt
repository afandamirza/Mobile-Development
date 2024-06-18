package com.bangkit.recyclopedia.view.description

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.databinding.ActivityDescriptionBinding
import com.bangkit.recyclopedia.view.take_photo.TakePhotoActivity

class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.apply {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            rvTutorial.apply {
                val tutorialListAdapter = TutorialListAdapter()
                layoutManager = LinearLayoutManager(this@DescriptionActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = tutorialListAdapter
                tutorialListAdapter.submitList(
                    listOf(
                        R.drawable.image_video_1,
                        R.drawable.image_video_2,
                        R.drawable.image_description,
                        R.drawable.image_cardboard
                    )
                )
            }

            btnRecycling.setOnClickListener {
                val intent = Intent(this@DescriptionActivity, TakePhotoActivity::class.java)
                intent.putExtra(TakePhotoActivity.EXTRA_IS_TRASH, false)
                startActivity(intent)
            }

            btnThrowAway.setOnClickListener {
                val intent = Intent(this@DescriptionActivity, TransactionActivity::class.java)
                intent.putExtra(TransactionActivity.EXTRA_IS_CLAIM_POINT, false)
                startActivity(intent)
            }
        }
    }
}