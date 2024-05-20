package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.siabilik.databinding.FragmentAdminAccountApproveBinding

class AdminAccountApproveFragment : Fragment() {

    private lateinit var binding: FragmentAdminAccountApproveBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminAccountApproveBinding.inflate(inflater, container, false)

        return binding.root
    }

}