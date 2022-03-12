package com.example.MovieAppMVVM

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.MovieAppMVVM.adapter.PagerAdapter
import com.example.MovieAppMVVM.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initial()

    }

    @SuppressLint("ResourceAsColor")
    private fun initial() {
        binding.viewPager.adapter = PagerAdapter(supportFragmentManager,lifecycle)
        binding.tabLayout.tabIconTint = null

        TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position ->
            when(position){
                0->{
                    tab.setIcon(R.drawable.movie)
                    tab.text = "All Movie"
                    tab.icon?.setTint(ContextCompat.getColor(this,R.color.red))

                }
                1->{
                    tab.setIcon(R.drawable.person)
                    tab.text = "Actor"
                    tab.icon?.setTint(ContextCompat.getColor(this, R.color.teal_700))
                    tab.icon!!.alpha = 70
                }
                2->{
                    tab.setIcon(R.drawable.favorite)
                    tab.text = "Favorites"
                    tab.icon?.setTint(ContextCompat.getColor(this,R.color.orange))
                    tab.icon!!.alpha = 70
                }
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab!!.icon!!.alpha = 250
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab!!.icon!!.alpha = 70
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                TODO("Not yet implemented")
            }

        })

    }


}