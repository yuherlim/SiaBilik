package com.example.siabilik.adminAcc.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.adminAcc.data.AccountApproveViewModel
import com.example.siabilik.databinding.FragmentAdminAccountApproveDetailsBinding
import com.example.siabilik.setImageBlob
import android.app.AlertDialog
import com.example.siabilik.UserManagement.SimpleEmail
import com.example.siabilik.hideKeyboard
import com.example.siabilik.snackbar

class AdminAccountApproveDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAdminAccountApproveDetailsBinding
    private val nav by lazy { findNavController() }
    private val accountID by lazy { arguments?.getString("accountID") ?: "" }

    private val accountVM : AccountApproveViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminAccountApproveDetailsBinding.inflate(inflater, container, false)

        val account = accountVM.get(accountID)
        if (account == null) {
            nav.navigateUp()
            return null
        }

        binding.accountProfileName.text = account.userName
        binding.accountProfileEmail.text = account.email
        binding.accountProfileImg.setImageBlob(account.profilePic)
        //binding.imgFront.setImageBlob(account.)
        binding.imgSelfie.setImageBlob(account.selfiePhoto)

        binding.approveButton.setOnClickListener {
            account.verificationStatus = "Approved"
            accountVM.set(account)
            nav.navigateUp()
        }

        binding.rejectButton.setOnClickListener {
            // Update account status
            account.verificationStatus = "Rejected"
            accountVM.set(account)

            // Prompt user for rejection reason
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Enter Reason")

            // Set up the input
            val input = EditText(requireContext())
            builder.setView(input)

            // Set up the buttons
            builder.setPositiveButton("OK") { dialog, _ ->
                val enteredText = input.text.toString()
                if (enteredText.isNotBlank()) {
                    sendEmail(enteredText, account.email)
                } else {
                    snackbar("Please enter a reason.")
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
        }

        return binding.root
    }

    // Function to send an email
    private fun sendEmail(reason: String, email: String) {
        val subject = "Reason for Rejecting Account Verification"
        val content = """
        <p><b>Reason:</b></p>
        <h1 style="color: red">$reason</h1>
        <p>Thank you.</p>
    """.trimIndent()

        // Sending the email using SimpleEmail (assuming it's a valid utility)
        SimpleEmail()
            .to(email)
            .subject(subject)
            .content(content)
            .isHtml()
            .send {
                snackbar("Email sent.")
                nav.navigateUp()
            }

        snackbar("Sending email...")
    }


}