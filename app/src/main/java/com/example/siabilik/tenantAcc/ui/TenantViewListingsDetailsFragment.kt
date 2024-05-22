package com.example.siabilik.tenantAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.demo.data.AuthVM
import com.example.siabilik.R
import com.example.siabilik.adminAcc.LoggedInUserViewModel
import com.example.siabilik.data.Tenant
import com.example.siabilik.databinding.FragmentTenantViewListingsDetailsBinding
import com.example.siabilik.setImageBlob
import com.example.siabilik.tenantAcc.data.ListingViewModel


class TenantViewListingsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentTenantViewListingsDetailsBinding
    private val nav by lazy { findNavController() }
    private val listingId by lazy { arguments?.getString("listingId") ?: "" }

    private val listingVM: ListingViewModel by activityViewModels()
    private val userViewModel: LoggedInUserViewModel by activityViewModels()
    private val allUserViewModel: AuthVM by activityViewModels()

    var currentLoggedInTenant: Tenant? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTenantViewListingsDetailsBinding.inflate(inflater, container, false)

        val listing = listingVM.get(listingId)
        if (listing == null) {
            nav.navigateUp()
            return null
        }

        // TODO(10): Populate [listing]

        binding.imgListing.setImageBlob(listing.propertyPhoto)
        binding.txtOwner.text = listing.ownerID
        //set owner image
//        binding.imgUser.setImageBlob(owner.photo)
        val featureList = listing.features.split(',')
        var featureString = "Features:\n"
        featureList.forEach {
            featureString += "- ${it.trim()}\n"
        }
        binding.txtFeatures.text = featureString
        binding.txtTitle.text = listing.title
        binding.txtRental.text = "RM %.2f".format(listing.rental.toString().toFloat())
        binding.txtDescription.text = listing.description

        // Observe the LiveData
        userViewModel.loggedInUserLD.observe(viewLifecycleOwner, Observer { loggedInUser ->
            when (loggedInUser!!.userType) {
                "Tenant" -> {
                    currentLoggedInTenant = allUserViewModel.getTenantById(loggedInUser.userID)
                }
            }
        })

        binding.btnContactOwner.setOnClickListener {
            nav.navigate(
                R.id.tenantContactOwnerDetailsFragment, bundleOf(
                    "tenantId" to (currentLoggedInTenant?.id ?: ""),
                    "listingId" to listing.id
                )
            )
        }

        return binding.root
    }


}