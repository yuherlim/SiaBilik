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
import com.google.android.material.textfield.TextInputLayout
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
        val username = binding.txtUsername.text.toString().trim()
        val email = binding.txtEmail.text.toString().trim()
        val phone = binding.txtPhone.text.toString().trim()
        val password = binding.txtPassword.text.toString().trim()
        val confirmPassword = binding.txtForgotPassword.text.toString().trim() // Assuming there's a confirm password field

        binding.txtLayoutPassword.endIconMode = TextInputLayout.END_ICON_NONE
        binding.txtLayoutForgotPassword.endIconMode = TextInputLayout.END_ICON_NONE

        // Validate input
        if (!validateInput(username, email, phone, password, confirmPassword, userType)) {
            return
        }
        // Insert user
        if(true) {
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
                    nav.navigate(R.id.login)
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
                    nav.navigate(R.id.login)
                }
            }
            Toast.makeText(context, "Successfully registered", Toast.LENGTH_SHORT).show()
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
        var isPass = true
        if (TextUtils.isEmpty(username)) {
            binding.txtUsername.error = "Username is required"
            isPass = false
        }
        if (username.length < 5) {
            binding.txtUsername.error = "Username must be at least 5 characters"
            isPass = false
        }
        if (TextUtils.isEmpty(email)) {
            binding.txtEmail.error = "Email is required"
            isPass = false
        }
        if (!isValidEmail(email)) {
            binding.txtEmail.error = "Invalid email format"
            isPass = false
        }
        if (TextUtils.isEmpty(phone)) {
            binding.txtPhone.error = "Phone number is required"
            isPass = false
        }
        if (!isValidPhone(phone)) {
            binding.txtPhone.error = "Invalid phone number format. Must start from 0 and contain 8-10 digits."
            isPass = false
        }
        if (TextUtils.isEmpty(password)) {
            binding.txtPassword.error = "Password is required"
            isPass = false
        }
        if (!isValidPassword(password)) {
            binding.txtPassword.error = "Password must contain at least 8 characters, including at least 1 upper/lower case, number, and special character"
            binding.txtLayoutPassword.endIconMode = TextInputLayout.END_ICON_NONE
            isPass = false
        }
        if (password != confirmPassword) {
            binding.txtForgotPassword.error = "Passwords do not match"
            binding.txtLayoutForgotPassword.endIconMode = TextInputLayout.END_ICON_NONE
            isPass = false
        }
        if (TextUtils.isEmpty(userType)) {
            Toast.makeText(context, "Please select a user type", Toast.LENGTH_SHORT).show()
            isPass = false
        }
        if (isPass){
            return true
        }else{
            return false
        }

    }


    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun isValidPhone(phone: String): Boolean {
        val phonePattern = "^0\\d{1,2}-?\\d{7,8}|01[0-46-9]-?\\d{7,8}\$"
        return phone.matches(phonePattern.toRegex())
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#\$%^&*(),.?\":{}|<>]{8,}$"
        return password.matches(passwordPattern.toRegex())
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