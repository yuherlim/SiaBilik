package com.example.siabilik.UserManagement

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.demo.data.AuthVM
import com.example.siabilik.MainActivity
import com.example.siabilik.R
import com.example.siabilik.databinding.FragmentLoginBinding
import com.example.siabilik.errorDialog
import kotlinx.coroutines.launch


class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val nav by lazy { findNavController() }
    private val auth: AuthVM by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        // -----------------------------------------------------------------------------------------
        var userType : String = "Tenant"
    //Default check tenant button at first run
        binding.tbUserType.check(binding.tenantButton.id)
        //Check on the checked button
        binding.tbUserType.addOnButtonCheckedListener { buttons, checkedId, isChecked ->
            // Perform actions based on the selected RadioButton
            if(isChecked == true) {
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

            binding.forgotPassword.setOnClickListener { forgotPassword() }
            binding.login.setOnClickListener { login(userType) }
            binding.registerTextPart2.setOnClickListener { Register() }

            // -----------------------------------------------------------------------------------------
            return binding.root
        }

            private fun forgotPassword() {
                nav.navigate(R.id.forgotPassword2, bundleOf(
                ))
            }

        private fun login(userType: String) {

            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()
            if (username == "" || password == "") {
                errorDialog("UserName or Password shouldnt be empty")
            } else {
                // TODO(3): Login -> auth.login(...)
                //          Clear navigation backstack
                lifecycleScope.launch {
                    val loginResult = auth.login(username, password, userType)
                    when(loginResult){
                        //REMEMBER FIX THIS
                        /*"NA" -> errorDialog("Invalid login credentials.")*/
                        "Tenant" -> {
                            nav.navigate(R.id.tenantViewListingsFragment, bundleOf(
                                "userID" to "userID",
                                "userType" to "userType"
                            ))
                        } // remember to change both of the layout
                        "Owner" -> {
                            nav.navigate(R.id.ownerMyListing, bundleOf(
                                "userID" to "userID",
                                "userType" to "userType"
                            ))
                        }
                        "Admin" -> {
                            nav.navigate(R.id.adminListingApproveFragment, bundleOf(
                                "userID" to "userID",
                                "userType" to "userType"
                            ))
                }}}
            }
        }

        private fun Register() {
            nav.navigate(R.id.registerFragment, bundleOf(
            ))
        }
    }