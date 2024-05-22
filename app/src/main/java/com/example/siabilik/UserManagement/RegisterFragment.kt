package com.example.siabilik.UserManagement

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import com.example.demo.data.AuthVM
import com.example.siabilik.MainActivity
import com.example.siabilik.R
import com.example.siabilik.data.Owner
import com.example.siabilik.data.Tenant
import com.example.siabilik.databinding.FragmentLoginBinding
import com.example.siabilik.databinding.FragmentRegisterBinding
import com.example.siabilik.errorDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var firestore: FirebaseFirestore
    private val vm: AuthVM by activityViewModels()
    private val nav by lazy { findNavController() }
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var userTypeToggleGroup: MaterialButtonToggleGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)
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

        binding.backButton.setOnClickListener{nav.navigateUp()}

        firestore = FirebaseFirestore.getInstance()
        binding.register.setOnClickListener {
            if (R.id.txtPassword != R.id.txtLayoutForgotPassword) {
                register(userType)

            }
        }

        return binding.root
    }


    private fun register(userType: String) {
        // Insert user
        when (userType) {
            "Tenant" -> {
                val user = Tenant(
                    userName = binding.txtUsername.text.toString().trim(),
                    email = binding.txtEmail.text.toString().trim(),
                    phoneNumber = binding.txtPhone.toString().trim(),
                    password = binding.txtPassword.text.toString().trim()
                )
//                val e = vm.validateTenant(user)
//                if (e != "") {
//                    errorDialog(e)
//                    return
//                }

                vm.addTenant(user)
                nav.navigateUp()
            }

            "Owner" -> {
                val user = Owner(
                    userName = binding.txtUsername.text.toString().trim(),
                    email = binding.txtEmail.text.toString().trim(),
                    phoneNumber = binding.txtPhone.toString().trim(),
                    password = binding.txtPassword.text.toString().trim()
                )
//                val e = vm.validateOwner(user)
//                if (e != "") {
//                    errorDialog(e)
//                    return
//                }
                vm.addOwner(user)
                nav.navigateUp()
            }
        }
    }

    private fun validateInput(
        username: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String,
        userType: String
    ): Boolean {
        if (TextUtils.isEmpty(username)) {
            usernameEditText.error = "Username is required"
            return false
        }
        if (TextUtils.isEmpty(email)) {
            emailEditText.error = "Email is required"
            return false
        }
        if (TextUtils.isEmpty(phone)) {
            phoneEditText.error = "Phone number is required"
            return false
        }
        if (TextUtils.isEmpty(password)) {
            passwordEditText.error = "Password is required"
            return false
        }
        if (password != confirmPassword) {
            confirmPasswordEditText.error = "Passwords do not match"
            return false
        }
        if (TextUtils.isEmpty(userType)) {
            Toast.makeText(context, "Please select a user type", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onResume() {
        // Hides bottom navigation
        requireActivity().findViewById<BottomNavigationView>(R.id.bv).visibility = View.GONE
        (requireActivity() as MainActivity).hideTopAppBar()
        super.onResume()
    }

    override fun onPause() {
        // Unhidden bottom navigation
        requireActivity().findViewById<BottomNavigationView>(R.id.bv).visibility = View.VISIBLE
        (requireActivity() as MainActivity).showTopAppBar()
        super.onPause()
    }
}