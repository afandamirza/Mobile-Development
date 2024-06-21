package com.bangkit.recyclopedia.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.recyclopedia.data.pref.UserPreference
import com.bangkit.recyclopedia.data.pref.dataStore
import com.bangkit.recyclopedia.databinding.ActivityMainBinding
import com.bangkit.recyclopedia.view.ViewModelFactory
import com.bangkit.recyclopedia.view.homepage.HomeActivity
import com.bangkit.recyclopedia.view.welcome.WelcomePageActivity


class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        playAnimation()
        setupViewModel()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        var intent: Intent
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(intent)
                    finish()
                }, DELAY_MILLIS)
            } else {
                intent = Intent(this, WelcomePageActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(intent)
                    finish()
                }, DELAY_MILLIS)
            }
        }
    }

    private fun playAnimation() {

        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(100)

        AnimatorSet().apply {
            playSequentially(message)
            startDelay = 100
        }.start()
    }
    companion object {
        private const val DELAY_MILLIS: Long = 2000
    }
}