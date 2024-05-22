package com.example.siabilik.ownerAcc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.R
import com.example.siabilik.cropToBlob
import com.example.siabilik.data.Listing
import com.example.siabilik.databinding.FragmentOwnerAddListingBinding
import com.example.siabilik.databinding.FragmentOwnerEditListingBinding
import com.example.siabilik.ownerAcc.data.ListingViewModel
import com.example.siabilik.setImageBlob
import com.example.siabilik.toast


class OwnerEditListing : Fragment() {
    private lateinit var binding: FragmentOwnerEditListingBinding
    private val nav by lazy { findNavController() }
    private val listingVM: ListingViewModel by activityViewModels()
    private val listingID by lazy { arguments?.getString("listingID") ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnerEditListingBinding.inflate(inflater, container, false)
        bindData()
        binding.propertyPhoto.setOnClickListener { selectPropertyPhoto() }
        binding.ownershipPhoto.setOnClickListener { selectOwnershipPhoto() }
        binding.confirmButton.setOnClickListener { editListing() }
        binding.cancelButton.setOnClickListener { nav.navigateUp() }

        return binding.root
    }


    fun validation() {}

    fun editListing() {
        var selectedListing = listingVM.getListingById(listingID)
        if (selectedListing != null) {  var newListing = Listing(
            id = selectedListing.id,
            title = binding.txtTitle.text.toString(),
            features = binding.txtFeatures.text.toString(),
            description = binding.txtDescription.text.toString(),
            rental = String.format("%.2f", binding.txtRental.text.toString().toDouble()).toDouble(),
            status = selectedListing.status,
            approvalStatus = selectedListing.approvalStatus,
            ownerID = selectedListing.ownerID,
            address = binding.txtAdress.text.toString(),
            ownershipProof = binding.ownershipPhoto.cropToBlob(1000, 1000),
            propertyPhoto = binding.propertyPhoto.cropToBlob(1000, 1000)
        )

            listingVM.setListing(newListing)
            toast("Listing edited")
            nav.navigateUp()

        }




    }

    private val getPropertyPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.propertyPhoto.setImageURI(it)
    }

    private fun selectPropertyPhoto() {
        getPropertyPhoto.launch("image/*")
    }

    private val getOwnerShipPhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            binding.ownershipPhoto.setImageURI(it)
        }

    private fun selectOwnershipPhoto() {
        getOwnerShipPhoto.launch("image/*")
    }

    fun bindData() {
        var selectedListing = listingVM.getListingById(listingID)
        if (selectedListing != null) {
            binding.txtListingID.setText(selectedListing.id)
            binding.txtListingID.isEnabled = false
            binding.txtTitle.setText(selectedListing.title)
            binding.txtDescription.setText(selectedListing.description)
            binding.txtFeatures.setText(selectedListing.features)
            binding.txtAdress.setText(selectedListing.address)
            binding.propertyPhoto.setImageBlob(selectedListing.propertyPhoto)
            binding.ownershipPhoto.setImageBlob(selectedListing.ownershipProof)
            binding.txtRental.setText(String.format("%.2f", selectedListing.rental.toDouble()))
        }
    }


}