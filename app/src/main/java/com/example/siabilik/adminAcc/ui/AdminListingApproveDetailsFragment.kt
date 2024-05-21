package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.siabilik.databinding.FragmentAdminListingApproveDetailsBinding

class AdminListingApproveDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAdminListingApproveDetailsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminListingApproveDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }
}