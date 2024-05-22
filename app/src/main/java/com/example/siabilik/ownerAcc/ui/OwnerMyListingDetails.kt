package com.example.siabilik.ownerAcc.ui

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.siabilik.R
import com.example.siabilik.cropToBlob
import com.example.siabilik.databinding.FragmentOwnerListingDetailsBinding
import com.example.siabilik.databinding.FragmentOwnerMyListingBinding
import com.example.siabilik.databinding.FragmentOwnerMylistingDetailsBinding
import com.example.siabilik.ownerAcc.data.ListingViewModel
import com.example.siabilik.ownerAcc.util.CardViewListingAdapter
import com.example.siabilik.setImageBlob


class OwnerMyListingDetails : Fragment() {
    private lateinit var binding: FragmentOwnerMylistingDetailsBinding
    private val nav by lazy { findNavController() }
    private val listingVM: ListingViewModel by activityViewModels()
    private val listingID by lazy { arguments?.getString("listingID",) ?: ""}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnerMylistingDetailsBinding.inflate(inflater, container, false)
        bindData()
        binding.editButton.setOnClickListener{
            nav.navigate(R.id.ownerEditListing, bundleOf(
                "listingID" to listingID
            ))
        }
        binding.availableButton.setOnClickListener{
            var selectedListing = listingVM.getListingById(listingID)
            if (selectedListing!=null){
                if(selectedListing.status=="Unavailable"){
                    selectedListing.status="Available"
                }else{
                    selectedListing.status="Unavailable"
                }

                listingVM.setListing(selectedListing)
                bindData()
            }

        }
        return binding.root
    }

    fun bindData() {
        var selectedListing = listingVM.getListingById(listingID)
        if (selectedListing != null) {
            binding.title.text = selectedListing.title
            binding.Listingstatus.text = selectedListing.status
            binding.Approvalstatus.text = selectedListing.approvalStatus
            val featureList = selectedListing.features.split(',')
            var featureString = "Features:\n"
            featureList.forEach {
                featureString += "- ${it.trim()}\n"
            }
            binding.features.text = featureString
            binding.address.text = selectedListing.address
            binding.rental.text = String.format("RM %.2f", selectedListing.rental.toDouble())
            binding.propertyPhoto.setImageBlob(selectedListing.propertyPhoto)
            binding.ownershipPhoto.setImageBlob(selectedListing.ownershipProof)
            binding.description.text = selectedListing.description
            if(selectedListing.approvalStatus == "Pending" || selectedListing.approvalStatus == "Rejected"  ){
                binding.availableButton.visibility= View.INVISIBLE
                binding.editButton.visibility=View.INVISIBLE
            }

            if(selectedListing.approvalStatus == "Approved" && selectedListing.status == "Unavailable"){
                binding.availableButton.visibility= View.INVISIBLE
                binding.editButton.visibility=View.INVISIBLE
            }
        }




    }


}