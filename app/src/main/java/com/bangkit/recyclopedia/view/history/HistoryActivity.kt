
package com.bangkit.recyclopedia.view.history

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.recyclopedia.data.pref.UserPreference
import com.bangkit.recyclopedia.data.pref.dataStore
import com.bangkit.recyclopedia.databinding.ActivityHistoryBinding
import com.bangkit.recyclopedia.view.ViewModelFactory

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val historyListAdapter: HistoryAdapter by lazy { HistoryAdapter() }
    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = historyListAdapter

        }

        setupViewModel()
    }

    private fun setupViewModel() {
        val userPreference = UserPreference.getInstance(this.dataStore)
        val factory = ViewModelFactory(userPreference, applicationContext)
        viewModel = ViewModelProvider(this, factory).get(HistoryViewModel::class.java)

        viewModel.getUser()?.observe(this, { historyItems ->
            viewModel.getAllStories().observe(this) {
                historyListAdapter.submitData(lifecycle, it)
            }
        })

    }


}