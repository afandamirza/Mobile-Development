package com.bangkit.recyclopedia.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.recyclopedia.data.model.UserSignUpModel
import com.bangkit.recyclopedia.data.pref.UserPreference
import com.bangkit.recyclopedia.data.pref.dataStore
import com.bangkit.recyclopedia.databinding.ActivitySignupBinding
import com.bangkit.recyclopedia.view.ViewModelFactory

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var signupViewModel: SignupViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
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

    private fun setupViewModel(){
        signupViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[SignupViewModel::class.java]

        signupViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        signupViewModel.finishActivity.observe(this) {
            finish()
        }

    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText
            val email = binding.emailEditText
            val password = binding.passwordEditText

            if(name.text.toString().isEmpty()) {
                name.error = "Masukkan Username!"
            }

            if(email.text.toString().isEmpty()) {
                email.error = "Masukkan Email!"
            }

            if(password.text.toString().isEmpty()) {
                password.error = "Masukkan Password!"
            }

            if (name.error == null && email.error == null && password.error == null) {
                val user = UserSignUpModel(name.text.toString(), email.text.toString(), password.text.toString())
                signupViewModel.signupUser(user, this)
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        val progressBar = binding.progressBar
        val progressText = binding.progressTextMain

        if (isLoading) {
            progressBar.visibility = View.VISIBLE
            progressText.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
            progressText.visibility = View.GONE
        }
    }

    private fun playAnimation() {
        val titleWelcome =
            ObjectAnimator.ofFloat(binding.register, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                titleWelcome,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 100
        }.start()
    }

}