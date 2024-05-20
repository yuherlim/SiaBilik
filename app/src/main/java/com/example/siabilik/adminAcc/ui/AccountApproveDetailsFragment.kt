package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.siabilik.R
import com.example.siabilik.databinding.FragmentAccountApproveDetailsBinding

class AccountApproveDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAccountApproveDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountApproveDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }
}