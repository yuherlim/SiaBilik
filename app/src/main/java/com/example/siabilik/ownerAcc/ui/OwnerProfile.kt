package com.example.siabilik.ownerAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.R
import com.example.siabilik.adminAcc.LoggedInUserViewModel
import com.example.siabilik.databinding.FragmentOwnerProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class OwnerProfile : Fragment() {

    private lateinit var binding:FragmentOwnerProfileBinding
    private val nav by lazy { findNavController() }

    private val userViewModel: LoggedInUserViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOwnerProfileBinding.inflate(inflater, container, false)

        binding.clEditProfile.setOnClickListener {
            nav.navigate(R.id.editProfile)
        }

        binding.clEditPassword.setOnClickListener {
            nav.navigate(R.id.editPassword)
        }

        binding.clLogout.setOnClickListener {
            userViewModel.clearData()
            //clear back stack every time enter top level destination
            nav.popBackStack(R.id.login, true)
            requireActivity().findViewById<BottomNavigationView>(R.id.bv).menu.clear()
            nav.navigate(R.id.login)

        }

        return binding.root
    }

}