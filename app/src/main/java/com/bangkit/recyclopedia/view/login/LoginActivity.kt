package com.bangkit.recyclopedia.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.recyclopedia.data.model.UserLoginModel
import com.bangkit.recyclopedia.data.model.UserModel
import com.bangkit.recyclopedia.data.pref.UserPreference
import com.bangkit.recyclopedia.data.pref.dataStore
import com.bangkit.recyclopedia.databinding.ActivityLoginBinding
import com.bangkit.recyclopedia.view.ViewModelFactory
import com.bangkit.recyclopedia.view.homepage.HomeActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
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
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) { userModel ->
            this.userModel = userModel
        }

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        loginViewModel.finishActivity.observe(this) {
            if (it == true) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
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

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText
            val password = binding.passwordEditText

            if(email.text.toString().isEmpty()) {
                email.error = "Masukkan Email!"
            }

            if(password.text.toString().isEmpty()) {
                password.error = "Masukkan Password!"
            }

            if (email.error == null && password.error == null) {
                val user = UserLoginModel(email.text.toString(), password.text.toString())
                loginViewModel.loginUser(user, this)
            }
        }
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.login, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                title,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 100
        }.start()
    }


}