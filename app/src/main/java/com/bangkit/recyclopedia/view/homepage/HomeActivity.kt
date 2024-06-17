package com.bangkit.recyclopedia.view.homepage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.data.pref.UserPreference
import com.bangkit.recyclopedia.data.pref.dataStore
import com.bangkit.recyclopedia.databinding.ActivityHomeBinding
import com.bangkit.recyclopedia.view.ViewModelFactory
import com.bangkit.recyclopedia.view.welcome.WelcomePageActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()

    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore), this)
        )[HomeViewModel::class.java]
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logout -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Logout Confirmation")
                    setMessage("Are you sure you want to logout form your account?")
                    setPositiveButton("Yes") { _, _ ->
                        homeViewModel.logout()
                        val intent = Intent(context, WelcomePageActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    setNegativeButton("No") {_, _ ->
                    }
                    create()
                    show()
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            setTitle("Exit Application")
            setMessage("Are you sure you want to exit the application?")
            setPositiveButton("Yes") { _, _ ->
                super.onBackPressed()
                finishAffinity()
            }
            setNegativeButton("No") {_, _ ->
                // Do Nothing
            }
            create()
            show()
        }
    }
}