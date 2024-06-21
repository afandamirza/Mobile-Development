package com.bangkit.recyclopedia.view.classification

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.databinding.ActivityFailedResultBinding
import com.bangkit.recyclopedia.databinding.ActivityResultClassificationBinding
import com.bangkit.recyclopedia.view.homepage.HomeActivity
import com.bangkit.recyclopedia.view.takephoto.TakePhotoActivity

class FailedResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFailedResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFailedResultBinding.inflate(layoutInflater)
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

            btnCobaLagi.setOnClickListener {
                finish()
                val intent = Intent(this@FailedResultActivity, TakePhotoActivity::class.java)
                intent.putExtra(TakePhotoActivity.EXTRA_IS_TRASH, false)
                startActivity(intent)
            }

            btnKembali.setOnClickListener {
                finish()
                val intent = Intent(this@FailedResultActivity, HomeActivity::class.java)
                startActivity(intent)
            }

            back.setOnClickListener {
                finish()
                val intent = Intent(this@FailedResultActivity, TakePhotoActivity::class.java)
                intent.putExtra(TakePhotoActivity.EXTRA_IS_TRASH, false)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun displayResult() {

        val intruksiErrorFormattedText = """
            <p>Jika masih terus terjadi error: <p>
            <ul>
                <li> Coba login ulang kembali</li>
            </ul>
        """.trimIndent()
        binding.intruksiError.text = Html.fromHtml(intruksiErrorFormattedText, Html.FROM_HTML_MODE_LEGACY)

    }

}

