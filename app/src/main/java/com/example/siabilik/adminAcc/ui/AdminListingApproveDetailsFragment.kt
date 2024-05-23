package com.example.siabilik.adminAcc.ui

import android.app.AlertDialog
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.demo.data.AuthVM
import com.example.siabilik.UserManagement.SimpleEmail
import com.example.siabilik.adminAcc.data.ListingApproveViewModel
import com.example.siabilik.databinding.FragmentAdminListingApproveDetailsBinding
import com.example.siabilik.setImageBlob
import com.example.siabilik.snackbar

class AdminListingApproveDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAdminListingApproveDetailsBinding
    private val nav by lazy { findNavController() }
    private val listingID by lazy { arguments?.getString("listingID") ?: "" }

    private val listingVM : ListingApproveViewModel by activityViewModels()
    private val vm : AuthVM by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminListingApproveDetailsBinding.inflate(inflater, container, false)

        val listing = listingVM.get(listingID)
        if(listing == null){
            nav.navigateUp()
            return null
        }

        val owner = vm.getOwnerById(listing.ownerID)
        if (owner == null) {
            nav.navigateUp()
            return null
        }

        // Setting up the UI with the listing details
        binding.txtTitle.text = listing.title
        binding.imgListing.setImageBlob(listing.propertyPhoto)
        val featuresList = listing.features.split(',')
        val sb = SpannableStringBuilder()
        for (feature in featuresList) {
            sb.append("- ").append(feature.trim()).append("\n")
        }
        binding.txtFeatures.text = sb
        binding.txtRentalPrice.text = listing.rental.toString()
        binding.txtAddress.text = listing.address
        binding.txtDescription.text = listing.description
        binding.proofImage.setImageBlob(listing.ownershipProof)

        binding.btnApprove.setOnClickListener {
            listing.approvalStatus = "Approved"
            listing.status = "Available"
            listingVM.set(listing)
            nav.navigateUp()
        }

        binding.btnReject.setOnClickListener {
            listing.approvalStatus = "Rejected"
            listingVM.set(listing)
            showRejectionReasonDialog(owner.email)
        }

        return binding.root
    }

    private fun showRejectionReasonDialog(email: String) {
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
                sendEmail(enteredText, email)
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

    private fun sendEmail(reason: String, email: String) {
        val subject = "Reason for Rejecting Listing Verification"
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
                // Navigate up after the email is sent
                nav.navigateUp()
            }

        snackbar("Sending email...")
    }
}
