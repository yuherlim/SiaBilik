package com.example.siabilik.UserManagement

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.demo.data.AuthVM
import com.example.siabilik.R
import com.example.siabilik.data.TENANT
import com.example.siabilik.data.Tenant
import com.example.siabilik.databinding.FragmentForgotPasswordBinding
import com.example.siabilik.databinding.FragmentLoginBinding
import com.example.siabilik.errorDialog


class ForgotPassword : Fragment() {

    private lateinit var email: String
    private lateinit var binding: FragmentForgotPasswordBinding
    private val nav by lazy { findNavController() }
    private val auth: AuthVM by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        var userType: String = ""
        //Default check tenant button at first run
        binding.tbUserType.check(binding.tenantButton.id)
        userType = "Tenant"
        //Check on the checked button
        binding.tbUserType.addOnButtonCheckedListener { buttons, checkedId, isChecked ->
            // Perform actions based on the selected RadioButton
            if (isChecked == true) {
                when (checkedId) {
                    binding.tenantButton.id -> {
                        userType = "Tenant"
                        Log.d("MyTag", "Tenant button clicked")
                    }

                    binding.ownerButton.id -> {
                        userType = "Owner"
                        Log.d("MyTag", "Owner button clicked")
                    }
                }
            }
        }
        binding.next.setOnClickListener { resetPassword(userType) }

        return binding.root
    }

    private fun resetPassword(userType: String) {

        email = binding.txtEmail.text.toString().trim()

        //Email Validation
        when (userType) {
            "Tenant" -> {
                var tenant = auth.getTenantByEmail(email)
                if (tenant == null) {
                    // More specific error message
                    errorDialog("Email no found")
                } else {
                    //var newPassword = random number
                    //tenant.password = newPassword
                    auth.setTenant(tenant) //update owner
                    // use property in the tenant such as name to construct an email to notify the user the new password
                    // Send email to user
                    //display toast to inform user the password is reset
                    nav.navigateUp()
                }
            }

            "Owner" -> {
                var owner = auth.getOwnerByEmail(email)
                if (owner == null) {
                    // More specific error message
                    errorDialog("Email no found")
                } else {
                    //var newPassword = random number
                    //owner.password = newPassword
                    auth.setOwner(owner) //update owner
                    // use property in the owner such as name to construct an email to notify the user the new password
                    // Send email to user
                    //display toast to inform user the password is reset
                    nav.navigateUp()
                }
            }
        }
    }
}

