package com.bangkit.recyclopedia.view.history

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.recyclopedia.R
import com.bangkit.recyclopedia.databinding.ActivityHistoryBinding
import com.google.android.material.tabs.TabLayout
import java.util.Date

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val historyListAdapter: HistoryListAdapter by lazy { HistoryListAdapter() }

    private val listHistory: List<History> = listOf(
        History("You recycled a Bottle", Date(2021, 9, 18, 17, 20, 21), Status.DONE),
        History("You recycled a Glass", Date(2021, 9, 19, 13, 19, 1), Status.DONE),
        History("You recycled a Cardboard", Date(2021, 8, 18, 17, 20, 12), Status.DONE),
        History("You throw a Glass", Date(2021, 8, 19, 17, 20, 12), Status.DONE),
        History("You recycle a Bottle", Date(2021, 8, 18), Status.ON_PROGRESS),
        History("You recycle a Glass ", Date(2021, 8, 19), Status.ON_PROGRESS),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.historyTabLayout.addTab(binding.historyTabLayout.newTab().setText("Done"))
        binding.historyTabLayout.addTab(binding.historyTabLayout.newTab().setText("On Progress"))
        val tabCount: Int = binding.historyTabLayout.tabCount
        for (i in 0 until tabCount) {
            val tabView: View = (binding.historyTabLayout.getChildAt(0) as ViewGroup).getChildAt(i)
            tabView.requestLayout()
            ViewCompat.setBackground(tabView, setImageButtonStateNew(this));
            ViewCompat.setPaddingRelative(tabView, tabView.paddingStart, tabView.paddingTop, tabView.paddingEnd, tabView.paddingBottom);
        }
        binding.historyTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        historyListAdapter.submitList(listHistory.filter { it.status == Status.DONE })
                    }
                    1 -> {
                        historyListAdapter.submitList(listHistory.filter { it.status == Status.ON_PROGRESS })
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = historyListAdapter

            historyListAdapter.submitList(listHistory.filter { it.status == Status.DONE })
        }
    }

    private fun setImageButtonStateNew(mContext: Context): StateListDrawable {
        val states = StateListDrawable()
        states.addState(intArrayOf(android.R.attr.state_selected), ContextCompat.getDrawable(mContext, R.drawable.tab_bg_selected_green))
        states.addState(intArrayOf(-android.R.attr.state_selected), ContextCompat.getDrawable(mContext, R.drawable.tab_bg_normal))

        return states
    }
}