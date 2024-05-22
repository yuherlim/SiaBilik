package com.example.siabilik.UserManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.siabilik.adminAcc.LoggedInUserViewModel
import com.example.siabilik.databinding.FragmentEditPasswordBinding


class EditPassword : Fragment() {

    private lateinit var binding: FragmentEditPasswordBinding
    private val nav by lazy { findNavController() }
    private val userType by lazy { arguments?.getString("userType") ?: "" }
    private val userID by lazy { arguments?.getString("userID") ?: "" }
    private val userViewModel: LoggedInUserViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditPasswordBinding.inflate(layoutInflater, container, false)

//        var userID = ""
//        var userType =""
//        userViewModel.loggedInUserLD.observe(this, Observer { loggedInUser ->
//            userID=loggedInUser.userID
//            userType=loggedInUser.userType
//        })
//        binding.txtUsername.setText(userID)
//


        return binding.root
    }

    private fun editPassword(){

    }
}