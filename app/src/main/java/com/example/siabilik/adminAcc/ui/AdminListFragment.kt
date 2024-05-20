package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.siabilik.databinding.FragmentAdminListBinding


class AdminListFragment : Fragment() {

    private lateinit var binding: FragmentAdminListBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminListBinding.inflate(inflater, container, false)

        return binding.root
    }
}