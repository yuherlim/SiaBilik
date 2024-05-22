package com.example.siabilik.UserManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.adminAcc.LoggedInUserViewModel
import com.example.siabilik.adminAcc.data.AdminViewModel
import com.example.siabilik.databinding.FragmentEditProfileBinding
import androidx.lifecycle.Observer

class EditProfile : Fragment() {


    private lateinit var binding:FragmentEditProfileBinding
    private val nav by lazy { findNavController() }

    private val userViewModel: LoggedInUserViewModel by activityViewModels()

    //all three view mode declare here
    private val adminVM : AdminViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(layoutInflater,container, false)

        // Observe the LiveData
        userViewModel.loggedInUserLD.observe(viewLifecycleOwner, Observer { loggedInUser ->
            when (loggedInUser.userType) {
                "Owner" -> {
                    //search owner using ownerViewModel
                }
                "Tenant" -> {
                    //search tenant using tenantViewModel
                }
                "Admin" -> {

                }
            }
        })




        //set binding text and soon
        binding.txtUsername.setText("Sia Yeong Sheng")
        return binding.root
    }


}