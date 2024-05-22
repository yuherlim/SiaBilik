package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.adminAcc.data.AdminViewModel
import com.example.siabilik.adminAcc.data.ListingApproveViewModel
import com.example.siabilik.databinding.FragmentAdminListingApproveDetailsBinding
import com.example.siabilik.setImageBlob

class AdminListingApproveDetailsFragment : Fragment() {

    private lateinit var binding: FragmentAdminListingApproveDetailsBinding
    private val nav by lazy { findNavController() }
    private val listingID by lazy { arguments?.getString("listingID") ?: "" }

    private val listingVM : ListingApproveViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminListingApproveDetailsBinding.inflate(inflater, container, false)

        val listing = listingVM.get(listingID)
        if(listing == null){
            nav.navigateUp()
            return null
        }

        binding.txtTitle.text = listing.title
        //binding.imgListing.setImageBlob(listing)
        val featuresList = listing.features.split(',')
        val sb = SpannableStringBuilder()
        for (feature in featuresList) {
            sb.append("- ").append(feature.trim()).append("\n")
        }
        binding.txtFeatures.text = sb
        binding.txtRentalPrice.text = listing.rental.toString()
        binding.txtAddress.text = listing.address
        binding.txtDescription.text = listing.description
        //binding.proofImage.setImageBlob(listing)


        binding.btnApprove.setOnClickListener {
            listing.approvalStatus = "Approved"
            listingVM.set(listing)
            nav.navigateUp()
        }

        binding.btnReject.setOnClickListener {
            listing.approvalStatus = "Rejected"
            listingVM.set(listing)
            nav.navigateUp()
            //maybe can prompt a dialog to input reason and send email
        }
        return binding.root
    }



}