package com.example.siabilik.UserManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.siabilik.databinding.FragmentEditPasswordBinding


class EditPassword : Fragment() {

    private lateinit var binding: FragmentEditPasswordBinding
    private val nav by lazy { findNavController() }
    private val userType by lazy { arguments?.getString("userType") ?: "" }
    private val userID by lazy { arguments?.getString("userID") ?: "" }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditPasswordBinding.inflate(layoutInflater, container, false)





        return binding.root
    }
}