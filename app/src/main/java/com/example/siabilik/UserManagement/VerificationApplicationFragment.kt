package com.example.siabilik.UserManagement

import android.os.Bundle
import android.provider.ContactsContract.Contacts.Photo
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.demo.data.AuthVM
import com.example.siabilik.R
import com.example.siabilik.cropToBlob
import com.example.siabilik.data.Tenant
import com.example.siabilik.databinding.FragmentRegisterBinding
import com.example.siabilik.databinding.FragmentVerificationApplicationBinding
import com.example.siabilik.ownerAcc.data.ListingViewModel
import com.example.siabilik.toast


class VerificationApplicationFragment : Fragment() {

    private lateinit var binding: FragmentVerificationApplicationBinding
    private val nav by lazy { findNavController() }
    private val vm: AuthVM by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerificationApplicationBinding.inflate(inflater, container, false)
        var photoType : String
        binding.tbUserType.check(binding.studentIDButton.id)
        binding.tbUserType.addOnButtonCheckedListener { buttons, checkedId, isChecked ->
            // Perform actions based on the selected RadioButton
            if (isChecked == true) {
                when (checkedId) {
                    binding.studentIDButton.id -> {
                        photoType = "Student ID"
                        binding.photo.setOnClickListener{selectStudentIDPhoto()}
                        Log.d("MyTag", "Student ID button clicked")
                        binding.submit.setOnClickListener{addStudentID()}
                    }

                    binding.selfieButton.id -> {
                        photoType = "Selfie"
                        binding.photo.setOnClickListener{selectSelfiePhoto()}
                        Log.d("MyTag", "Selfie button clicked")
                    }
                }
            }

        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun addStudentID(){

        var studentID = Tenant(
        studentID = binding.photo.cropToBlob(1000,1000)
        )

        vm.setPhoto(studentID)
        toast( "Student ID added")

        nav.navigate(R.id.selfieButton)
    }

    private fun addSelfiePhoto(){

        var selfie = Tenant(
            selfiePhoto = binding.photo.cropToBlob(1000,1000)
        )

        vm.setPhoto(selfie)
        toast( "Selfie Photo added")
        binding.submit.setOnClickListener{addSelfiePhoto()}
        nav.navigate(R.id.frameLayout2)//Change the Navigate Destination
    }

    private val getStudentIDPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.photo.setImageURI(it)
    }

    private fun selectStudentIDPhoto() {
        getStudentIDPhoto.launch("image/*")
    }

    private val getSelfiePhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.photo.setImageURI(it)
    }

    private fun selectSelfiePhoto() {
        getSelfiePhoto.launch("image/*")
    }

}