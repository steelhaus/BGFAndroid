package com.example.boardgamefinder.presentation.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.boardgamefinder.R
import com.example.boardgamefinder.databinding.ActivityMainBinding

/**
 * Activity for holding all app tabs
 */
internal class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        // setting fragments for main frame
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                }
                R.id.nav_search -> {
                    replaceFragment(SearchFragment())
                }
                R.id.nav_add -> {
                    replaceFragment(NewEventFragment())
                }
                R.id.nav_notifications -> {
                    replaceFragment(NotificationsFragment())
                }
                R.id.nav_profile -> {
                    replaceFragment(ProfileFragment())
                }
            }
            true
        }
    }

    // used for switching between fragments in main frame
    fun replaceFragment(fragment: Fragment) {
        // open fragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()
            .replace(R.id.main_frame, fragment)

        if(fragment !is HomeFragment &&
            fragment !is SearchFragment &&
            fragment !is NewEventFragment &&
            fragment !is NotificationsFragment &&
            fragment !is ProfileFragment)
            fragmentTransaction.addToBackStack( "tag" )

        fragmentTransaction.commit()
    }
}