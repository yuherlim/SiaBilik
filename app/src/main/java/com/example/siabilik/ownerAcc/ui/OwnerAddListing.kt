package com.example.siabilik.ownerAcc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.R
import com.example.siabilik.cropToBlob
import com.example.siabilik.data.Listing
import com.example.siabilik.databinding.FragmentOwnerAddListingBinding
import com.example.siabilik.databinding.FragmentOwnerMyListingBinding
import com.example.siabilik.ownerAcc.data.ListingViewModel
import com.example.siabilik.toast


class OwnerAddListing : Fragment() {
    private lateinit var binding: FragmentOwnerAddListingBinding
    private val nav by lazy { findNavController() }
    private val listingVM: ListingViewModel by activityViewModels()

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


    fun validation() {}

    fun addListing() {
        val newListingID = newListingID()
        var newListing = Listing(
            id = newListingID,
            title = binding.txtTitle.text.toString(),
            features = binding.txtFeatures.text.toString(),
            description = binding.txtDescription.text.toString(),
            //rental = String.format("%.2f", binding.txtRental.text.toString().toDouble()).toDouble(),
            status = "Unavailable",
            approvalStatus ="Pending",
            ownerID = "test",
            address = binding.txtAdress.text.toString(),
            ownershipProof = binding.ownershipPhoto.cropToBlob(1000,1000),
            propertyPhoto = binding.propertyPhoto.cropToBlob(1000,1000)
        )

        listingVM.setListing(newListing)
        toast( "New Listing added")
        nav.navigateUp()

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



}