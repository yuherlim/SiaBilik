package com.example.siabilik.UserManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
import com.example.siabilik.adminAcc.LoggedInUserViewModel
import com.example.siabilik.databinding.FragmentEditPasswordBinding
import com.example.demo.data.AuthVM
import com.example.siabilik.snackbar
import com.example.siabilik.toast
import com.google.android.material.textfield.TextInputLayout


class EditPassword : Fragment() {

    private lateinit var binding: FragmentEditPasswordBinding
    private val nav by lazy { findNavController() }


    private val userViewModel: LoggedInUserViewModel by activityViewModels()
    private val allUserViewModel: AuthVM by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditPasswordBinding.inflate(layoutInflater, container, false)

        var currentPassword = ""
        var password = ""
        var confirmPassword = ""
        var userOriPassword = ""

        // Observe the LiveData
        userViewModel.loggedInUserLD.observe(viewLifecycleOwner, Observer { loggedInUser ->
            when (loggedInUser.userType) {
                "Owner" -> {
                    val user = allUserViewModel.getOwnerById(loggedInUser.userID)
                    userOriPassword = user!!.password
                    binding.txtUsername.setText(user!!.userName)
                }
                "Tenant" -> {
                    val user = allUserViewModel.getTenantById(loggedInUser.userID)
                    userOriPassword = user!!.password
                    binding.txtUsername.setText(user!!.userName)
                }
                "Admin" -> {
                    val user = allUserViewModel.getAdminById(loggedInUser.userID)
                    userOriPassword = user!!.password
                    binding.txtUsername.setText(user!!.userName)
                }
            }
        })



        binding.button.setOnClickListener {

            currentPassword = binding.txtPassword.text.toString()
            password = binding.txtNewPassword.text.toString()
            confirmPassword = binding.txtConfirmPassword.text.toString()

            if(validatePassword(currentPassword,password,confirmPassword,userOriPassword)) {

                userViewModel.loggedInUserLD.observe(viewLifecycleOwner, Observer { loggedInUser ->
                    when (loggedInUser.userType) {
                        "Owner" -> {
                            val user = allUserViewModel.getOwnerById(loggedInUser.userID)
                            userOriPassword = user!!.password
                            user!!.password = password
                            allUserViewModel.setOwner(user)
                        }
                        "Tenant" -> {
                            val user = allUserViewModel.getTenantById(loggedInUser.userID)
                            userOriPassword = user!!.password
                            user!!.password = password
                            allUserViewModel.setTenant(user)
                        }
                        "Admin" -> {
                            val user = allUserViewModel.getAdminById(loggedInUser.userID)
                            userOriPassword = user!!.password
                            user!!.password = password
                            allUserViewModel.setAdmin(user)
                        }
                    }
                })
                toast("Password Updated")
                binding.txtPassword.text?.clear()
                binding.txtNewPassword.text?.clear()
                binding.txtConfirmPassword.text?.clear()

                binding.txtPassword.requestFocus()
            }
        }



        return binding.root
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#\$%^&*(),.?\":{}|<>]{8,}$"
        return password.matches(passwordPattern.toRegex())
    }

    private fun validatePassword(currentPassword : String, password: String, confirmPassword: String, userOriPassword: String) : Boolean {
        var isPass = true

        if(currentPassword != userOriPassword){
            binding.txtPassword.error = "Password not match with current password"
            binding.txtLayoutPassword.endIconMode = TextInputLayout.END_ICON_NONE
            isPass = false
        }

        if (!isValidPassword(password)) {
            binding.txtNewPassword.error = "Password must contain at least 8 characters, including at least 1 upper/lower case, number, and special character"
            binding.txtLayoutNewPassword.endIconMode = TextInputLayout.END_ICON_NONE
            isPass = false
        }

        if (password != confirmPassword) {
            binding.txtConfirmPassword.error = "Passwords do not match"
            binding.txtLayoutConfirmPassword.endIconMode = TextInputLayout.END_ICON_NONE
            isPass = false
        }

        return isPass
    }
}