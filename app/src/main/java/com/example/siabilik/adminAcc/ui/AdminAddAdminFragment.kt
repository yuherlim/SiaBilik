package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.adminAcc.data.AdminViewModel
import com.example.siabilik.cropToBlob
import com.example.siabilik.data.Admin
import com.example.siabilik.databinding.FragmentAdminAddAdminBinding

class AdminAddAdminFragment : Fragment() {

    private lateinit var binding: FragmentAdminAddAdminBinding
    private val nav by lazy { findNavController() }
    private val adminVM : AdminViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminAddAdminBinding.inflate(inflater, container, false)

    binding.uploadPhotoButton.setOnClickListener { select() }
    binding.doneButton.setOnClickListener { submit() }


        return binding.root
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.adminProfile.setImageURI(it)
    }

    private fun select() {
        getContent.launch("image/*")
    }

    private fun submit(){

        var adminId : Int = 0

        // Get the latest value
        val latestAdmin = adminVM.getLatestAdmin()
        if (latestAdmin != null) {
            adminId = latestAdmin.id.removePrefix("Admin").toIntOrNull()!!
            adminId = adminId.plus(1)
        }

        val a = Admin(
            //get id
            id = "Admin${adminId.toString().padStart(3, '0')}",
            userName = binding.edtAdminName.text.toString().trim(),
            email = binding.editTextTextEmailAddress.text.toString().trim(),
            password = binding.editTextTextPassword.text.toString().trim(),
            phoneNumber = binding.editTextPhone.text.toString().trim(),
            adminPhoto = binding.adminProfile.cropToBlob(300,300)
        )

        val e = adminVM.validate(a)
        if(e != ""){
            //display error message
            return
        }

        adminVM.set(a)
        nav.navigateUp()
    }

}