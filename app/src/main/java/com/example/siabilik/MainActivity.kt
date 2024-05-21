package com.example.siabilik

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.siabilik.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val nav by lazy { supportFragmentManager.findFragmentById(R.id.host)!!.findNavController() }

    private lateinit var abc: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        abc = AppBarConfiguration(
            setOf(
                R.id.frameLayout2,
                R.id.frameLayout3,
                R.id.loginLayout,
                R.id.relativeLayout,
            )
        )

        // Action bar and bottom nav
        setSupportActionBar(binding.topAppBar)
        setupActionBarWithNavController(nav, abc)
        binding.bv.setupWithNavController(nav)

    }

    //Action bar up button
    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp() || super.onSupportNavigateUp()
    }




}