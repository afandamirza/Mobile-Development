package com.bangkit.recyclopedia.view.classification

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.databinding.ActivityResultClassificationBinding
import com.bangkit.recyclopedia.view.takephoto.TakePhotoActivity

class ClassificationResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultClassificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultClassificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        displayResult()
    }

    private fun initView() {
        binding.apply {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            rvTutorial.apply {
                val tutorialListAdapter = ClassificationResultAdapter()
                layoutManager = LinearLayoutManager(this@ClassificationResultActivity, LinearLayoutManager.HORIZONTAL, false)
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
                val intent = Intent(this@ClassificationResultActivity, TakePhotoActivity::class.java)
                intent.putExtra(TakePhotoActivity.EXTRA_IS_TRASH, false)
                startActivity(intent)
            }

//            btnThrowAway.setOnClickListener {
//                val intent = Intent(this@ClassificationResultActivity, TransactionActivity::class.java)
//                intent.putExtra(TransactionActivity.EXTRA_IS_CLAIM_POINT, false)
//                startActivity(intent)
//            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun displayResult() {
        val result = intent.getStringExtra("result")
        val congrats = intent.getStringExtra("congrats")
        val confidenceScore = intent.getDoubleExtra("confidenceScore", 0.0)

        binding.yourRubbishText.text = result
        binding.congratsMessage.text = congrats
       
    }


}