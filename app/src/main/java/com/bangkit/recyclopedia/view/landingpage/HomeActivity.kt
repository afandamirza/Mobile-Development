package com.bangkit.recyclopedia.view.landingpage

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }
}