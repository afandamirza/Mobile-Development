package com.bangkit.recyclopedia.view.classification

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.recyclopedia.databinding.ActivityTrashActionBinding
import com.bangkit.recyclopedia.view.history.HistoryActivity
import com.bangkit.recyclopedia.view.homepage.HomeActivity

class TrashActionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrashActionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTrashActionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        val isClaimPoint = intent.getBooleanExtra(EXTRA_IS_CLAIM_POINT, false)
        val action = intent.getStringExtra(EXTRA_ACTION)

        binding.apply {
            tvTitle.text = if (isClaimPoint) {
                "Point Claimed"
            } else {
                "Thank You!"
            }
            tvDecription.text = if (isClaimPoint) {
                "+Rp 100.00"
            } else {
                when (action) {
                    ACTION_RECYCLING -> "Semoga hasil daur ulangmu bermanfaat!"
                    ACTION_THROW_AWAY -> "Jangan lupa untuk membuang sampah pada tempatnya!"
                    else -> "Don't forget to throw your\ntrash in the bin."
                }
            }

            btnHistory.setOnClickListener {
                finish()
                val intent = Intent(this@TrashActionActivity, HistoryActivity::class.java)
                startActivity(intent)
            }

            btnHome.setOnClickListener {
                finish()
                val intent = Intent(this@TrashActionActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_IS_CLAIM_POINT = "extra_is_claim_point"
        const val EXTRA_ACTION = "extra_action"
        const val ACTION_RECYCLING = "recycling"
        const val ACTION_THROW_AWAY = "throw_away"
    }
}