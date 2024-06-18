package com.bangkit.recyclopedia.view.description

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.recyclopedia.databinding.ActivityTransactionBinding
import com.bangkit.recyclopedia.view.history.HistoryActivity

class TransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {

        val isClaimPoint = intent.getBooleanExtra(EXTRA_IS_CLAIM_POINT, false)
        binding.apply {
            tvTitle.text = if (isClaimPoint) {
                "Point Claimed"
            } else {
                "Thank You!"
            }
            tvDecription.text = if (isClaimPoint) {
                "+Rp 100.00"
            } else {
                "Don't forget to throw your\ntrash in the bin."
            }

            btnHistory.setOnClickListener {
                val intent = Intent(this@TransactionActivity, HistoryActivity::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_IS_CLAIM_POINT = "extra_is_claim_point"
    }
}