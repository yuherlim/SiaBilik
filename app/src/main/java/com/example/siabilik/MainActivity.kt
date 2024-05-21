package com.example.siabilik

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.siabilik.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val nav by lazy { supportFragmentManager.findFragmentById(R.id.host)!!.findNavController() }
    private lateinit var abc: AppBarConfiguration
    private val tenantTLD = setOf(
        R.id.tenantViewListingsFragment,
        R.id.tenantViewStarredListingsFragment,
        R.id.tenantViewRequestsFragment,
        R.id.tenantAccountFragment
    )
    private val ownerTLD = setOf(
        R.id.ownerMyListing,
        R.id.ownerListing,
        R.id.ownerProfile,
    )
    private val adminTLD = setOf(
        R.id.adminListingApproveFragment,
        R.id.adminListFragment,
        R.id.adminAccountApproveFragment,
        R.id.adminProfileFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Insert code to get user type here. Pass in user type to configureNavigationBasedOnUserType


        configureNavigationBasedOnUserType("Admin")

        // Action bar and bottom nav
        setSupportActionBar(binding.topAppBar)
        setupActionBarWithNavController(nav, abc)
        binding.bv.setupWithNavController(nav)


    }

    private fun configureNavigationBasedOnUserType(s: String) {
        configureABC(s)
        configureBottomNav(s)
    }

    private fun configureABC(s: String) {
        when (s) {
            "Tenant" -> { abc = AppBarConfiguration(tenantTLD) }
            "Owner" -> { abc = AppBarConfiguration(ownerTLD) }
            "Admin" -> { abc = AppBarConfiguration(adminTLD) }
        }
    }

    private fun configureBottomNav(s: String) {
        when (s) {
            "Tenant" -> { binding.bv.inflateMenu(R.menu.tenant_bottom_nav_menu) }
            "Owner" -> { binding.bv.inflateMenu(R.menu.owner_bottom_nav_menu) }
            "Admin" -> { binding.bv.inflateMenu(R.menu.admin_bottom_nav_menu) }
            else -> Unit
        }
    }

    //Action bar up button
    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp(abc) || super.onSupportNavigateUp()
    }


}