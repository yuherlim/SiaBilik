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
import com.example.siabilik.adminAcc.LoggedInUserViewModel
import com.example.siabilik.cropToBlob
import com.example.siabilik.data.Tenant
import com.example.siabilik.databinding.FragmentVerificationApplicationBinding
import com.example.siabilik.toast
import androidx.lifecycle.Observer


class VerificationApplicationFragment : Fragment() {

    private lateinit var binding: FragmentVerificationApplicationBinding
    private val nav by lazy { findNavController() }

    private val userViewModel: LoggedInUserViewModel by activityViewModels()
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



        // Observe the LiveData
        userViewModel.loggedInUserLD.observe(viewLifecycleOwner, Observer { loggedInUser ->
            if (loggedInUser!!.userType == "Tenant") {
                val user = vm.getTenantById(loggedInUser.userID)
                binding.tbUserType.check(binding.studentIDButton.id)
                binding.tbUserType.addOnButtonCheckedListener { buttons, checkedId, isChecked ->
                    // Perform actions based on the selected RadioButton
                    if (isChecked == true) {
                        when (checkedId) {
                            binding.studentIDButton.id -> {
                                photoType = "Student ID"
                                binding.photo.setOnClickListener{selectStudentIDPhoto()}
                                user!!.studentID = binding.photo.cropToBlob(500,500)
                                Log.d("MyTag", "Student ID button clicked")
                                binding.submit.setOnClickListener{
                                    vm.setTenant(user)
                                }
                            }

                            binding.selfieButton.id -> {
                                photoType = "Selfie"
                                binding.photo.setOnClickListener{selectSelfiePhoto()}
                                user!!.selfiePhoto = binding.photo.cropToBlob(500,500)
                                Log.d("MyTag", "Selfie button clicked")
                                binding.submit.setOnClickListener {
                                    vm.setTenant(user)
                                }

                            }
                        }
                    }

                }
                }
            }
        )



        // Inflate the layout for this fragment
        return binding.root
    }

//    private fun addStudentID(){
//
//        binding
//        var studentID = Tenant(
//        studentID = binding.photo.cropToBlob(1000,1000)
//        )
//
//        vm.setPhoto(studentID)
//        toast( "Student ID added")
//
//        nav.navigate(R.id.selfieButton)
//    }
//
//    private fun addSelfiePhoto(){
//
//        var selfie = Tenant(
//            selfiePhoto = binding.photo.cropToBlob(1000,1000)
//        )
//
//        vm.setPhoto(selfie)
//        toast( "Selfie Photo added")
//        binding.submit.setOnClickListener{addSelfiePhoto()}
//        nav.navigate(R.id.frameLayout2)//Change the Navigate Destination
//    }

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