package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.siabilik.databinding.FragmentListingApproveBinding

class ListingApproveFragment : Fragment() {

    private lateinit var binding: FragmentListingApproveBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListingApproveBinding.inflate(inflater, container, false)

        return binding.root
    }
}