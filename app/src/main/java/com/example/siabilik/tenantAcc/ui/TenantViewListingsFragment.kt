package com.example.siabilik.tenantAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.siabilik.R
import com.example.siabilik.databinding.FragmentTenantViewListingsBinding
import com.example.siabilik.tenantAcc.data.ListingViewModel
import com.example.siabilik.tenantAcc.util.GridViewListingAdapter


class TenantViewListingsFragment : Fragment() {

    private lateinit var binding: FragmentTenantViewListingsBinding
    private val nav by lazy { findNavController() }
    private val listingVM : ListingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTenantViewListingsBinding.inflate(inflater, container, false)
        // -----------------------------------------------------------------------------------------

        //clear back stack every time enter top level destination
        nav.popBackStack(R.id.tenantViewListingsFragment, false)

        val adapter = GridViewListingAdapter { h, listing ->
            h.binding.root.setOnClickListener{ detail(listing.id) }
        }

        binding.rvTenantViewListings.adapter = adapter
//        binding.rvTenantViewListings.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listingVM.getListingLD().observe(viewLifecycleOwner) { it ->
            adapter.submitList(it.filter { it.status == "Available" && it.approvalStatus == "Approved" })
        }

        // -----------------------------------------------------------------------------------------
        return binding.root

    }

    private fun detail(listingId: String) {
        nav.navigate(
            R.id.tenantViewListingsDetailsFragment, bundleOf(
                "listingId" to listingId
            )
        )
    }
}