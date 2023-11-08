package com.example.x_rates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.x_rates.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        //navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)
        binding.bottomNavigationView.setupWithNavController(navController)


        /*binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_baseline_assessment -> {
                    replaceFragment(FavorableRateFragment())
                    true
                }

                R.id.bottom_mdi_bank -> {
                    replaceFragment(NbtRateFragment())
                    true
                }

                else -> false
            }
        }
        replaceFragment(FavorableRateFragment())

    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    */
    }
}