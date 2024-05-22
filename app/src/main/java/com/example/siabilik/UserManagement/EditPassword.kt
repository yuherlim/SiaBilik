package com.example.siabilik.UserManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
<<<<<<< HEAD
import androidx.navigation.fragment.findNavController
=======
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
>>>>>>> e45439bd91856adb0ce5e190c77cb0921a813cdd
import com.example.siabilik.adminAcc.LoggedInUserViewModel
import com.example.siabilik.databinding.FragmentEditPasswordBinding
import androidx.lifecycle.Observer
import com.example.demo.data.AuthVM


class EditPassword : Fragment() {

    private lateinit var binding: FragmentEditPasswordBinding
    private val nav by lazy { findNavController() }


    private val userViewModel: LoggedInUserViewModel by activityViewModels()
    private val allUserViewModel: AuthVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditPasswordBinding.inflate(layoutInflater, container, false)

<<<<<<< HEAD
        // Observe the LiveData
        userViewModel.loggedInUserLD.observe(viewLifecycleOwner, Observer { loggedInUser ->
            when (loggedInUser.userType) {
                "Owner" -> {
                    val user = allUserViewModel.getOwnerById(loggedInUser.userID)
                    binding.txtUsername.setText(user!!.userName)

                    val password = binding.txtPassword.text.toString()
                    val confirmPassword = binding.txtConfirmPassword.text.toString()


                }
                "Tenant" -> {
                    val user = allUserViewModel.getTenantById(loggedInUser.userID)
                    binding.txtUsername.setText(user!!.userName)
                }
                "Admin" -> {
                    val user = allUserViewModel.getAdminById(loggedInUser.userID)
                    binding.txtUsername.setText(user!!.userName)
                }
            }


        })


        return binding.root
    }

    private fun editPassword(){

    }
}