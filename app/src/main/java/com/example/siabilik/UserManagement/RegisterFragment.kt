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
import com.example.demo.data.AuthVM
import com.example.siabilik.R
import com.example.siabilik.data.Owner
import com.example.siabilik.data.Tenant
import com.example.siabilik.databinding.FragmentRegisterBinding
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private  val vm: AuthVM by activityViewModels()
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
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        usernameEditText = view.findViewById(R.id.txtUsername)
        emailEditText = view.findViewById(R.id.txtEmail)
        phoneEditText = view.findViewById(R.id.txtPhone)
        passwordEditText = view.findViewById(R.id.txtPassword)
        confirmPasswordEditText = view.findViewById(R.id.txtForgotPassword)
        registerButton = view.findViewById(R.id.register)
        userTypeToggleGroup = view.findViewById(R.id.tbUserType)

        registerButton.setOnClickListener {
            /*registerUser()*/
        }
        binding.register.setOnClickListener { if (R.id.txtPassword != R.id.txtLayoutForgotPassword){
            register(userType)
        }
        }

        return view
    }

/*    private fun registerUser() {
        val username = usernameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()
        val userType = when (userTypeToggleGroup.checkedButtonId) {
            R.id.tenantButton -> "Tenant"
            R.id.ownerButton -> "Owner"
            else -> ""
        }

        if (validateInput(username, email, phone, password, confirmPassword, userType)) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = hashMapOf(
                            "username" to username,
                            "email" to email,
                            "phone" to phone,
                            "userType" to userType
                        )

                        firestore.collection("users").document(auth.currentUser!!.uid)
                            .set(user)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                                // Navigate to another activity or fragment
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Failed to save user data", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(context, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }*/

    private fun register(userType: String) {

        // Insert user
       when(userType){
           "Tenant" -> {
               val user = Tenant(
                   userName     = binding.txtUsername.text.toString().trim(),
                   email    = binding.txtEmail.text.toString().trim(),
                   phoneNumber    = binding.txtPhone.toString().trim(),
                   password = binding.txtPassword.text.toString().trim()
               )
               val e = vm.validateTenant(user)
               if (e != null) {
                   /*errorDialog(e)*/
                   return
               }

               vm.setTenant(user)
               nav.navigateUp()
           }
           "Owner" -> {
               val user = Owner(
               userName     = binding.txtUsername.text.toString().trim(),
               email    = binding.txtEmail.text.toString().trim(),
               phoneNumber    = binding.txtPhone.toString().trim(),
               password = binding.txtPassword.text.toString().trim()
               )
               val e = vm.validateOwner(user)
               if (e != null) {
                   /*errorDialog(e)*/
                   return
               }

               vm.setOwner(user)
               nav.navigateUp()
           }
       }

    }

    private fun validateInput(username: String, email: String, phone: String, password: String, confirmPassword: String, userType: String): Boolean {
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
}