package com.example.siabilik

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.siabilik.adminAcc.LoggedInUserViewModel
import com.example.siabilik.databinding.ActivityMainBinding
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val nav by lazy { supportFragmentManager.findFragmentById(R.id.host)!!.findNavController() }
    private lateinit var abc: AppBarConfiguration

    private val userViewModel: LoggedInUserViewModel by viewModels()

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

        // Observe the LiveData
        userViewModel.loggedInUserLD.observe(this, Observer { loggedInUser ->
            when (loggedInUser.userType) {
                "Owner" -> {
                    configureNavigationBasedOnUserType("Owner")
                    setupActionBarWithNavController(nav, abc)
                }
                "Tenant" -> {
                    configureNavigationBasedOnUserType("Tenant")
                    setupActionBarWithNavController(nav, abc)
                }
                "Admin" -> {
                    configureNavigationBasedOnUserType("Admin")
                    setupActionBarWithNavController(nav, abc)
                }
                else -> setupActionBarWithNavController(nav, abc)
            }
        })

        //configureNavigationBasedOnUserType("Owner")

        // Action bar and bottom nav
        setSupportActionBar(binding.topAppBar)
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

    fun hideTopAppBar() {
        supportActionBar?.hide()
    }

    fun showTopAppBar() {
        supportActionBar?.show()
    }

}