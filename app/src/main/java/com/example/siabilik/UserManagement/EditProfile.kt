package com.example.siabilik.UserManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.adminAcc.data.AdminViewModel
import com.example.siabilik.databinding.FragmentEditProfileBinding

class EditProfile : Fragment() {


    lateinit var binding:FragmentEditProfileBinding
    private val nav by lazy { findNavController() }
    private val userType by lazy { arguments?.getString("userType") ?: "" }
    private val userID by lazy { arguments?.getString("userID") ?: "" }

    //all three view mode declare here
    private val adminVM : AdminViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(layoutInflater,container, false)

        if(userType == "Admin"){
            var admin = adminVM.get(userID)
        }
//        else if (userType == "Tenant")




        //set binding text and soon
        binding.txtUsername.setText("Sia Yeong Sheng")
        return binding.root
    }


}