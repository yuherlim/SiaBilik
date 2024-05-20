package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.siabilik.databinding.FragmentAdminDetailsBinding


class AdminDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAdminDetailsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }
}