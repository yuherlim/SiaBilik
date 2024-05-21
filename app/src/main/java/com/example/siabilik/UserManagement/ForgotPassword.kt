package com.example.siabilik.UserManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.siabilik.R
import com.example.siabilik.databinding.FragmentForgotPasswordBinding


class ForgotPassword : Fragment() {

    private lateinit var email : String
    private lateinit var binding: FragmentForgotPasswordBinding
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

    private fun resetPassword() {
        email = binding.txtEmail.text.toString().trim()
        if (email.isEmpty()) {
            binding.txtEmail.error = "Insert Your Username"
            binding.txtEmail.requestFocus()
        } else {
            // Check if cooldown is active


        }
    }
}