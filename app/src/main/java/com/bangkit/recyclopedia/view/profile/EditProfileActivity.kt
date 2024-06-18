package com.bangkit.recyclopedia.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.databinding.ActivityEditProfileBinding
import com.bangkit.recyclopedia.view.welcome.WelcomePageActivity

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
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

            ivAvatar.apply {
                load(R.drawable.user_image) {
                    crossfade(true)
                    placeholder(R.drawable.user_image)
                    error(R.drawable.user_image)
                }
            }

            btnUpdate.setOnClickListener {
                finish()
            }

            btnDeleteAccount.setOnClickListener {
                val intent = Intent(this@EditProfileActivity, WelcomePageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}