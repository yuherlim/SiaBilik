package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.siabilik.R
import com.example.siabilik.databinding.FragmentAdminProfileBinding


class AdminProfileFragment : Fragment() {

    private lateinit var binding: FragmentAdminProfileBinding
    private val nav by lazy { findNavController() }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminProfileBinding.inflate(inflater, container, false)

        binding.clEditProfile.setOnClickListener {
            nav.navigate(R.id.editProfile, bundleOf(
                "userType" to "Admin",
                // and also need to past the ID
            ))
        }

        binding.clEditPassword.setOnClickListener {
            nav.navigate(R.id.editPassword, bundleOf(
                "userType" to "Admin"
                // and also need to past the ID
            ))
        }

        binding.clLogout.setOnClickListener {
            nav.navigate(R.id.login)
        }

        return binding.root
    }
}