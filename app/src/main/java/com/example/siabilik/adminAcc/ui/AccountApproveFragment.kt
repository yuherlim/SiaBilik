package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.siabilik.R
import com.example.siabilik.databinding.FragmentAccountApproveBinding

class AccountApproveFragment : Fragment() {

    private lateinit var binding: FragmentAccountApproveBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountApproveBinding.inflate(inflater, container, false)

        return binding.root
    }

}