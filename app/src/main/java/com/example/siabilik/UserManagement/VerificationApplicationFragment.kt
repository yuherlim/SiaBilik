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
import androidx.lifecycle.lifecycleScope
import com.example.siabilik.setImageBlob
import com.google.firebase.firestore.Blob
import kotlinx.coroutines.launch


class VerificationApplicationFragment : Fragment() {

    private lateinit var binding: FragmentVerificationApplicationBinding
    private val nav by lazy { findNavController() }

    private val userViewModel: LoggedInUserViewModel by activityViewModels()
    private val vm: AuthVM by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerificationApplicationBinding.inflate(inflater, container, false)

        var studentID = Blob.fromBytes(ByteArray(0))
        var selfie = Blob.fromBytes(ByteArray(0))
        var imageType = ""

        var userLoggedInID = userViewModel.loggedInUserLD.value?.userID
        var user = userLoggedInID?.let { vm.getTenantById(it) }

        binding.tbUserType.check(binding.studentIDButton.id)
        binding.selfie.visibility = View.INVISIBLE
        binding.studentID.visibility = View.VISIBLE
        binding.tbUserType.addOnButtonCheckedListener { buttons, checkedId, isChecked ->
            // Perform actions based on the selected RadioButton
            if (isChecked == true) {
                when (checkedId) {

                    binding.studentIDButton.id -> {
                        binding.selfie.visibility = View.INVISIBLE
                        binding.studentID.visibility = View.VISIBLE

                    }

                    binding.selfieButton.id -> {
                        binding.studentID.visibility = View.INVISIBLE
                        binding.selfie.visibility = View.VISIBLE

                    }
                }
            }
        }

        binding.selfie.setOnClickListener {
            selectSelfiePhoto()
        }

        binding.studentID.setOnClickListener{

            selectStudentIDPhoto()
        }


        binding.submit.setOnClickListener { submit(studentID, selfie) }

        // Inflate the layout for this fragment
        return binding.root
    }


    private fun selectStudentIDPhoto() {
        getStudentIDPhoto.launch("image/*")
    }

    private val getStudentIDPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.studentID.setImageURI(it)
    }

    private fun selectSelfiePhoto() {
        getSelfiePhoto.launch("image/*")
    }

    private val getSelfiePhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.selfie.setImageURI(it)
    }

    private fun submit(studentID: Blob, selfie: Blob) {
        var userLoggedInID = userViewModel.loggedInUserLD.value?.userID
        var user = userLoggedInID?.let { vm.getTenantById(it) }

        if (user != null) {
            user.studentID = binding.studentID.cropToBlob(500,500)
            user.selfiePhoto = binding.selfie.cropToBlob(500,500)
            vm.setTenant(user)
            toast("Verification Application Submitted")
        }
        nav.navigateUp()


    }

}