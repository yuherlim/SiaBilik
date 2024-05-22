package com.example.siabilik.UserManagement
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.demo.data.AuthVM
import com.example.siabilik.MainActivity
import com.example.siabilik.R
import com.example.siabilik.adminAcc.LoggedInUserViewModel
import com.example.siabilik.databinding.FragmentLoginBinding
import com.example.siabilik.errorDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch


class Login : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val nav by lazy { findNavController() }
    private val auth: AuthVM by activityViewModels()
    private val userViewModel: LoggedInUserViewModel by activityViewModels()


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

            binding.forgotPasswordLabel.setOnClickListener { forgotPassword() }
            binding.loginButton.setOnClickListener { login(userType) }
            binding.registerTextPart2.setOnClickListener { Register() }

            // -----------------------------------------------------------------------------------------
            return binding.root
        }

            private fun forgotPassword() {
                nav.navigate(R.id.forgotPassword, bundleOf(
                ))
            }

        private fun login(userType: String) {

            val username = binding.txtUsername.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()
            if (username == "" || password == "") {
                errorDialog("UserName or Password shouldn't be empty")
            } else {
                // TODO(3): Login -> auth.login(...)
                //          Clear navigation backstack
                lifecycleScope.launch {
                    val loginResult = auth.login(username, password, userType)
                    val passwordPattern = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#\$%^&*(),.?\":{}|<>]{8,}$")
                    when(loginResult){

                        "NA" -> errorDialog("Invalid login credentials.")
                        "Tenant" -> {
                            if(!password.matches(passwordPattern)) {
                                nav.navigate(R.id.editPassword,bundleOf(
                                "userID" to "userID",
                                "userType" to "userType"
                                ))
                            }else {
                                nav.navigate(
                                    R.id.tenantViewListingsFragment, bundleOf(
                                        "userID" to "userID",
                                        "userType" to "userType"
                                    )
                                )
                            }
                        } // remember to change both of the layout
                        "Owner" -> {
                            if (!password.matches(passwordPattern)) {
                                nav.navigate(
                                    R.id.editPassword, bundleOf(
                                        "userID" to "userID",
                                        "userType" to "userType"
                                    )
                                )
                            } else {
                                nav.navigate(
                                    R.id.ownerMyListing, bundleOf(
                                        "userID" to "userID",
                                        "userType" to "userType"
                                    )
                                )
                            }

                        }
                        "Admin" -> {
                            userViewModel.setLoggedInUser(loginResult,"userID")
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