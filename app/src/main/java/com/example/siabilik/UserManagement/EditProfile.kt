package com.example.siabilik.UserManagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.demo.data.AuthVM
import com.example.siabilik.R
import com.example.siabilik.adminAcc.LoggedInUserViewModel
import com.example.siabilik.cropToBlob
import com.example.siabilik.databinding.FragmentEditProfileBinding
import com.example.siabilik.setImageBlob
import com.example.siabilik.toast
import com.google.android.material.textfield.TextInputLayout

class EditProfile : Fragment() {


    private lateinit var binding:FragmentEditProfileBinding
    private val nav by lazy { findNavController() }

    private val userViewModel: LoggedInUserViewModel by activityViewModels()
    private val allUserViewModel: AuthVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(layoutInflater,container, false)

        var email = ""
        var phone = ""

        // Observe the LiveData
        userViewModel.loggedInUserLD.observe(viewLifecycleOwner, Observer { loggedInUser ->
            when (loggedInUser!!.userType) {
                "Owner" -> {
                    val user = allUserViewModel.getOwnerById(loggedInUser!!.userID)
                    binding.txtUsername.setText(user!!.userName)
                    binding.txtEmail.setText(user!!.email)
                    binding.txtPhone.setText(user!!.phoneNumber)
                    binding.profileImage.setImageBlob(user!!.profilePic)
                }
                "Tenant" -> {
                    val user = allUserViewModel.getTenantById(loggedInUser!!.userID)
                    binding.txtUsername.setText(user!!.userName)
                    binding.txtEmail.setText(user!!.email)
                    binding.txtPhone.setText(user!!.phoneNumber)
                    binding.profileImage.setImageBlob(user!!.profilePic)
                }
                "Admin" -> {
                    val user = allUserViewModel.getAdminById(loggedInUser!!.userID)
                    binding.txtUsername.setText(user!!.userName)
                    binding.txtEmail.setText(user!!.email)
                    binding.txtPhone.setText(user!!.phoneNumber)
                    binding.profileImage.setImageBlob(user!!.adminPhoto)
                }
            }
        })

        binding.profileImage.setOnClickListener {
            select()
        }

        binding.button2.setOnClickListener { nav.navigateUp() }

        binding.button.setOnClickListener {
            email = binding.txtEmail.text.toString()
            phone = binding.txtPhone.text.toString()

            if(validateEmailAndPhone(email, phone)){

                // Observe the LiveData
                userViewModel.loggedInUserLD.observe(viewLifecycleOwner, Observer { loggedInUser ->
                    when (loggedInUser!!.userType) {
                        "Owner" -> {
                            val user = allUserViewModel.getOwnerById(loggedInUser!!.userID)
                            user!!.email = email
                            user!!.phoneNumber = phone
                            user!!.profilePic = binding.profileImage.cropToBlob(300,300)
                            allUserViewModel.setOwner(user)
                            binding.txtUsername.setText(user!!.userName)
                            binding.txtEmail.setText(user!!.email)
                            binding.txtPhone.setText(user!!.phoneNumber)
                            binding.profileImage.setImageBlob(user.profilePic)
                        }
                        "Tenant" -> {
                            val user = allUserViewModel.getTenantById(loggedInUser!!.userID)
                            user!!.email = email
                            user!!.phoneNumber = phone
                            user!!.profilePic = binding.profileImage.cropToBlob(300,300)
                            allUserViewModel.setTenant(user)
                            binding.txtUsername.setText(user!!.userName)
                            binding.txtEmail.setText(user!!.email)
                            binding.txtPhone.setText(user!!.phoneNumber)
                            binding.profileImage.setImageBlob(user.profilePic)
                        }
                        "Admin" -> {
                            val user = allUserViewModel.getAdminById(loggedInUser!!.userID)
                            user!!.email = email
                            user!!.phoneNumber = phone
                            user!!.adminPhoto = binding.profileImage.cropToBlob(300,300)
                            allUserViewModel.setAdmin(user)
                            binding.txtUsername.setText(user!!.userName)
                            binding.txtEmail.setText(user!!.email)
                            binding.txtPhone.setText(user!!.phoneNumber)
                            binding.profileImage.setImageBlob(user.adminPhoto)
                        }
                    }
                })
                toast("Updated")
                binding.button.setOnClickListener {nav.navigate(R.id.tenantAccountFragment)  }
            }
        }

        return binding.root
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.profileImage.setImageURI(it)
    }
    private fun select() {
        getContent.launch("image/*")
    }

    private fun validateEmailAndPhone(email :String, phone: String) : Boolean {
        var isPass = true

        if(!isValidEmail(email)){
            binding.txtEmail.error = "Invalid email format."
            binding.txtLayoutEmail.endIconMode = TextInputLayout.END_ICON_NONE
            isPass = false
        }

        if(!isValidPhone(phone)){
            binding.txtPhone.error = "Invalid phone format."
            binding.txtLayoutEmail.endIconMode = TextInputLayout.END_ICON_NONE
            isPass = false
        }

        return isPass

    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun isValidPhone(phone: String): Boolean {
        val phonePattern = "^0\\d{1,2}-?\\d{7,8}|01[0-46-9]-?\\d{7,8}\$"
        return phone.matches(phonePattern.toRegex())
    }
}