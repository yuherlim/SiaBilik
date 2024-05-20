package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.siabilik.databinding.FragmentListingApproveDetailsBinding

class ListingApproveDetailsFragment : Fragment() {

    private lateinit var binding: FragmentListingApproveDetailsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListingApproveDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }
}