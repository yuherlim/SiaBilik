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
import com.google.android.material.snackbar.Snackbar
import com.example.siabilik.UserManagement.SimpleEmail


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
        val email = binding.txtEmail.text.toString().trim()

        when (userType) {
            "Tenant" -> {
                val tenant = auth.getTenantByEmail(email)
                if (tenant == null) {
                    errorDialog("Email not found")
                } else {
                    val newPassword = generateRandomPassword()
                    tenant.password = newPassword
                    auth.setTenant(tenant)
                    sendEmail(email, tenant.userName, newPassword)
                    Toast.makeText(context, "Password has been reset and emailed to the user.", Toast.LENGTH_SHORT).show()
                    nav.navigateUp()
                }
            }
            "Owner" -> {
                val owner = auth.getOwnerByEmail(email)
                if (owner == null) {
                    errorDialog("Email not found")
                } else {
                    val newPassword = generateRandomPassword()
                    owner.password = newPassword
                    auth.setOwner(owner)
                    sendEmail(email, owner.userName, newPassword)
                    Toast.makeText(context, "Password has been reset and emailed to the user.", Toast.LENGTH_SHORT).show()

                    nav.navigateUp()
                }
            }
        }
    }

    private fun generateRandomPassword(): String {
        val chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        return (1..8)
            .map { chars.random() }
            .joinToString("")
    }

    private fun sendEmail(email: String, userName: String, newPassword: String) {
        val subject = "Password Reset"
        val content = """
        <p>Dear $userName,</p>
        <p>Your password has been reset. Your new password is:</p>
        <h2 style="color: blue">$newPassword</h2>
        <p>Please use this new password to log in.</p>
        <p>Thank you.</p>
    """.trimIndent()

        SimpleEmail()
            .to(email)
            .subject(subject)
            .content(content)
            .isHtml()
            .send {
                Snackbar.make(binding.root, "Email sent.", Snackbar.LENGTH_LONG).show()
            }

        Snackbar.make(binding.root, "Sending email...", Snackbar.LENGTH_LONG).show()
    }


}

