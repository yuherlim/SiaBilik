package com.example.siabilik.ownerAcc.ui

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.R
import com.example.siabilik.adminAcc.LoggedInUserViewModel
import com.example.siabilik.cropToBlob
import com.example.siabilik.data.Listing
import com.example.siabilik.databinding.FragmentOwnerAddListingBinding
import com.example.siabilik.databinding.FragmentOwnerMyListingBinding
import com.example.siabilik.ownerAcc.data.ListingViewModel
import com.example.siabilik.snackbar
import com.example.siabilik.toast
import com.google.android.material.textfield.TextInputLayout


class OwnerAddListing : Fragment() {
    private lateinit var binding: FragmentOwnerAddListingBinding
    private val nav by lazy { findNavController() }
    private val listingVM: ListingViewModel by activityViewModels()
    private val userViewModel: LoggedInUserViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnerAddListingBinding.inflate(inflater, container, false)

        binding.txtListingID.setText(newListingID())
        binding.txtListingID.isEnabled = false

        binding.propertyPhoto.setOnClickListener{selectPropertyPhoto()}
        binding.ownershipPhoto.setOnClickListener{selectOwnershipPhoto()}

        binding.confirmButton.setOnClickListener { addListing() }
        binding.cancelButton.setOnClickListener { nav.navigateUp() }

        return binding.root
    }


    fun validation() {
    }

    fun addListing() {
        val newListingID = newListingID()
        var usrID = userViewModel.loggedInUserLD!!.value?.userID
        val formattedValue: Double = if (binding.txtRental.text.toString().isNotEmpty()) {
            String.format("%.2f", binding.txtRental.text.toString().toDouble()).toDouble()
        } else {
            0.0
        }
        var newListing = usrID?.let {
            Listing(
                id = newListingID,
                title = binding.txtTitle.text.toString(),
                features = binding.txtFeatures.text.toString(),
                description = binding.txtDescription.text.toString(),
                rental = formattedValue,
                status = "Unavailable",
                approvalStatus ="Pending",
                ownerID = it,
                address = binding.txtAdress.text.toString(),
                ownershipProof = binding.ownershipPhoto.cropToBlob(1000,1000),
                propertyPhoto = binding.propertyPhoto.cropToBlob(1000,1000)
            )
        }

        if (newListing != null) {
            if(validateInput(newListing)) {
                listingVM.setListing(newListing)
                toast( "New Listing added")
                nav.navigateUp()
            }
        }


    }

    fun newListingID(): String {
        var latestListing = listingVM.getLatestListing()
        var id: Int = 1

        if (latestListing != null) {
            id = latestListing.id.removePrefix("list-").toIntOrNull()!!
            id = id.plus(1)
        }

        var newListingId = "list-${id.toString().padStart(6, '0')}"

        return newListingId
    }

    private val getPropertyPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.propertyPhoto.setImageURI(it)
    }

    private fun selectPropertyPhoto() {
        getPropertyPhoto.launch("image/*")
    }

    private val getOwnerShipPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.ownershipPhoto.setImageURI(it)
    }

    private fun selectOwnershipPhoto() {
        getOwnerShipPhoto.launch("image/*")
    }


    private fun validateInput( listing:Listing
    ): Boolean {
        var isPass = true
        if (TextUtils.isEmpty(listing.title)) {
            binding.txtLayoutTitle.error = "Title is required"
            isPass = false
        }
        if (TextUtils.isEmpty(listing.description)) {
            binding.txtDescription.error = "Description is required"
            isPass = false
        }
        if (TextUtils.isEmpty(listing.address)) {
            binding.txtAdress.error = "Address is required"
            isPass = false
        }
        if (TextUtils.isEmpty(listing.features)) {
            binding.txtFeatures.error = "Features is required"
            isPass = false
        }
        if (listing.rental.toDouble()==0.0) {
            binding.txtRental.error = "Rental cannot be 0.0"
            isPass = false
        }
        if (isValidString(listing.features)) {
            binding.txtFeatures.error = "Features only accept text, number and comma as delimeter"
            isPass = false
        }
        if (listing.propertyPhoto.toBytes().isEmpty()) {
            snackbar("Property Photo is required")
            isPass = false
        }

        if (listing.ownershipProof.toBytes().isEmpty()) {
            snackbar("Property Photo is required")
            isPass = false
        }
        if (isPass){
            return true
        }else{
            return false
        }
    }

    fun isValidString(input: String): Boolean {
        val regex = "^[a-zA-Z0-9,]+$"
        return input.matches(regex.toRegex())
    }


}