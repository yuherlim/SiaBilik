package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.siabilik.databinding.FragmentAdminProfileBinding


class AdminProfileFragment : Fragment() {

    private lateinit var binding: FragmentAdminProfileBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminProfileBinding.inflate(inflater, container, false)

        return binding.root
    }
}