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


class ForgotPassword : Fragment() {

    private lateinit var email : String
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
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    private suspend fun resetPassword() {
        var userType : String = ""
        //Default check tenant button at first run
        binding.tbUserType.check(binding.tenantButton.id)
        //Check on the checked button
        binding.tbUserType.addOnButtonCheckedListener { buttons, checkedId, isChecked ->
            // Perform actions based on the selected RadioButton
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
        email = binding.txtEmail.text.toString().trim()
        val success = auth.reset(userType,email)
        when(success){
            "Tenant" -> {

                nav.popBackStack(R.id.loginLayout, false)
                nav.navigateUp()
            }
            "Owner" -> {
                nav.popBackStack(R.id.loginLayout, false)
                nav.navigateUp()
            }
            /*"NA"-> */
        }
/*        if (email.isEmpty()) {
            binding.txtEmail.error = "Insert Your Email"
            binding.txtEmail.requestFocus()
        } else {
            // Check if cooldown is active


        }*/

    }
}