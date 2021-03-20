package com.example.ktdeveloperslife.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.ktdeveloperslife.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    val title: Array<String> = arrayOf("Latest", "Top", "Hot")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        viewPager.adapter = ViewPagerFragmentAdapter(this)

        TabLayoutMediator(
            tabLayout, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = title[position]
        }.attach()

    }

    class ViewPagerFragmentAdapter(
        fragmentActivity: FragmentActivity
    ) : FragmentStateAdapter(fragmentActivity) {


        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return LatestFragment()
                1 -> return TopFragment()
                2 -> return HotFragment()
            }
            return LatestFragment()
        }

        override fun getItemCount(): Int {
            return 3
        }

    }
}