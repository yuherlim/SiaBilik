package com.example.siabilik.adminAcc.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.adminAcc.data.AdminViewModel
import com.example.siabilik.data.Admin
import com.example.siabilik.databinding.FragmentAdminDetailsBinding
import com.example.siabilik.setImageBlob


class AdminDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAdminDetailsBinding
    private val nav by lazy { findNavController() }
    private val adminID by lazy { arguments?.getString("adminID") ?: "" }

    private val adminVM : AdminViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminDetailsBinding.inflate(inflater, container, false)


        val admin = adminVM.get(adminID)
        if (admin == null) {
            nav.navigateUp()
            return null
        }

        //set image
        binding.accountProfileImg.setImageBlob(admin.adminPhoto)
        binding.textName.text = admin.userName


        binding.deleteButton.setOnClickListener {
            adminVM.delete(adminID)
            nav.navigateUp()
        }



        return binding.root
    }
}