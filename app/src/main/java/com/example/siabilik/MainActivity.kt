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
<<<<<<< HEAD
    private lateinit var abc: AppBarConfiguration
=======
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val topLevelDestinations = setOf(
        R.id.tenantViewListingsFragment, R.id.tenantViewStarredListingsFragment, R.id.tenantViewRequestsFragment, R.id.tenantAccountFragment,
        )
>>>>>>> 95deef22cf7d7ffd930258bc43ca6491a2cdb4c4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


<<<<<<< HEAD
        abc = AppBarConfiguration(
            setOf(
                R.id.tenantViewListingsFragment,
                R.id.tenantViewStarredListingsFragment,
                R.id.tenantViewRequestsFragment,
                R.id.tenantAccountFragment,
            )
        )

        // Action bar and bottom nav
        setSupportActionBar(binding.topAppBar)
        setupActionBarWithNavController(nav, abc)
        binding.bv.setupWithNavController(nav)
=======
        setSupportActionBar(binding.topAppBar)
        // appBarConfiguration needed to initialize app bar so that up button won't be displayed at top level destinations
        appBarConfiguration = AppBarConfiguration(topLevelDestinations)
        setupActionBarWithNavController(nav, appBarConfiguration)






        binding.bv.setOnItemSelectedListener {

            when(it.itemId){

                R.id.listing -> replaceFragment(Listing())
                R.id.myListing -> replaceFragment(MyListing())
                R.id.profile -> replaceFragment(RegisterFragment())
                R.id.profile -> replaceFragment(RegisterFragment())
                else ->{

                }

            }
            true
        }
>>>>>>> 95deef22cf7d7ffd930258bc43ca6491a2cdb4c4

    }

    // Action bar up button
//    override fun onSupportNavigateUp(): Boolean {
//        return nav.navigateUp() || super.onSupportNavigateUp()
//    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main,fragment)
        fragmentTransaction.commit()
    }




}