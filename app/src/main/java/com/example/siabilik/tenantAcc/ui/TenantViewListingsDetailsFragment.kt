package com.example.siabilik.tenantAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.databinding.FragmentTenantViewListingsDetailsBinding
import com.example.siabilik.setImageBlob
import com.example.siabilik.tenantAcc.data.ListingViewModel


class TenantViewListingsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentTenantViewListingsDetailsBinding
    private val nav by lazy { findNavController() }
    private val listingId by lazy { arguments?.getString("listingId") ?: "" }

    private val listingVM: ListingViewModel by activityViewModels()
//    private val ownerVM: OwnerView by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTenantViewListingsDetailsBinding.inflate(inflater, container, false)

        val listing =listingVM.get(listingId)
        if (listing == null) {
            nav.navigateUp()
            return null
        }

        // TODO(10): Populate [listing]

        binding.imgListing.setImageBlob(listing.propertyPhoto)
        binding.txtOwner.text = listing.ownerID
        //set owner image
//        binding.imgUser.setImageBlob(owner.photo)
        var str = "This is an example text"
        val featureList = listing.features.split(',')
        var featureString = "Features:\n"
        featureList.forEach {
            featureString += "- ${it.trim()}\n"
        }
        binding.txtFeatures.text = featureString
        binding.txtTitle.text = listing.title
        binding.txtRental.text = "RM %.2f".format(listing.rental.toString().toFloat())
        binding.txtDescription.text = listing.description

        return binding.root
    }

}