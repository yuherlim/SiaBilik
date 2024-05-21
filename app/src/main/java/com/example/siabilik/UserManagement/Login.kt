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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.demo.data.AuthVM
import com.example.siabilik.MainActivity
import com.example.siabilik.R
import com.example.siabilik.databinding.FragmentLoginBinding
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

            binding.forgotPassword.setOnClickListener { forgotPassword() }
            binding.login.setOnClickListener { login(userType) }
            binding.registerTextPart2.setOnClickListener { Register() }

            // -----------------------------------------------------------------------------------------
            return binding.root
        }

            private fun forgotPassword() {
                nav.popBackStack(R.id.forgotPassword, false)
                nav.navigateUp()
            }

        private fun login(userType: String) {

            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()
            if (username == null || password == null) {
                /*errorDialog("Invalid login credentials.")*/
            } else {
                // TODO(3): Login -> auth.login(...)
                //          Clear navigation backstack
                lifecycleScope.launch {
                    val success = auth.login(username, password, userType)
                    when(userType){
                        //REMEMBER FIX THIS
                        /*"NA" -> errorDialog("Invalid login credentials.")*/
                        "Tenant" -> {
                            nav.popBackStack(R.id.tvlConstraintLayout, false)
                            nav.navigateUp()
                        } // remember to change both of the layout
                        "Owner" -> {
                            nav.popBackStack(R.id.relativeLayout, false)
                            nav.navigateUp()
                        }
                    }
                }
            }
        }

        private fun Register() {
            nav.popBackStack(R.id.frameLayout2, false)
            nav.navigateUp()
        }
    }