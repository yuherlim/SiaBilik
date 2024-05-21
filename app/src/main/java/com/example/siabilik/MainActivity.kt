package com.example.siabilik

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.siabilik.UserManagement.RegisterFragment
import com.example.siabilik.databinding.ActivityMainBinding
import com.example.siabilik.ownerAcc.ui.Listing
import com.example.siabilik.ownerAcc.ui.MyListing

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //private val nav by lazy { supportFragmentManager.findFragmentById(R.id.host)!!.findNavController() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Action bar
        //setupActionBarWithNavController(nav)

        binding.bv.setOnItemSelectedListener {

            when(it.itemId){

                R.id.listing -> replaceFragment(Listing())
                R.id.myListing -> replaceFragment(MyListing())
                R.id.profile -> replaceFragment(RegisterFragment())
                R.id.profile -> replaceFragment(com.example.siabilik.UserManagement.RegisterFragment())
                else ->{

                }

            }
            true
        }

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