package com.bangkit.recyclopedia.view.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.data.model.UserResetPasswordModel
import com.bangkit.recyclopedia.data.pref.UserPreference
import com.bangkit.recyclopedia.data.pref.dataStore
import com.bangkit.recyclopedia.databinding.ActivityProfileBinding
import com.bangkit.recyclopedia.view.ViewModelFactory
import com.bangkit.recyclopedia.view.history.HistoryActivity
import com.bangkit.recyclopedia.view.welcome.WelcomePageActivity

class ProfileActivity  : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        setupViewModel()
        initView()
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
        profileViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[ProfileViewModel::class.java]

        profileViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        profileViewModel.finishActivity.observe(this) {
            finish()
        }

        profileViewModel.finishActivity.observe(this) {
            if (it == true) {
                val intent = Intent(this, WelcomePageActivity::class.java)
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
        binding.btnUpdate.setOnClickListener {
            val name = binding.etName
            val email = binding.etEmail
            val password = binding.etPassword

            if (name.error == null && email.error == null && password.error == null) {
                val user = UserResetPasswordModel(name.text.toString(), email.text.toString(), password.text.toString())
                profileViewModel.resetPassword(user, this)
            }
        }

        binding.btnHistory.setOnClickListener {
            val intent = Intent(this@ProfileActivity, HistoryActivity::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            AlertDialog.Builder(this@ProfileActivity).apply {
                setTitle("Konfirmasi Logout")
                setMessage("Apakah Anda ingin Logout Akun?")
                setPositiveButton("Ya") { _, _ ->
                    profileViewModel.logout()
                    val intent = Intent(this@ProfileActivity, WelcomePageActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                setNegativeButton("Tidak") { _, _ -> }
                create()
                show()
            }
        }
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
                logout.apply {
                    load(R.drawable.baseline_logout_24) {
                        crossfade(true)
                        placeholder(R.drawable.baseline_logout_24)
                        error(R.drawable.baseline_logout_24)
                    }
                }


            }
        }
}
