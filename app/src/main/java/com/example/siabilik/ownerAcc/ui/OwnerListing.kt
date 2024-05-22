package com.example.siabilik.ownerAcc.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.siabilik.R
import com.example.siabilik.databinding.FragmentOwnerListingBinding
import com.example.siabilik.databinding.FragmentOwnerListingDetailsBinding
import com.example.siabilik.databinding.FragmentOwnerMyListingBinding
import com.example.siabilik.ownerAcc.data.ListingViewModel
import com.example.siabilik.ownerAcc.util.CardViewListingAdapter

class OwnerListing : Fragment() {

    private lateinit var binding: FragmentOwnerListingBinding
    private val nav by lazy { findNavController() }
    private val listingVM: ListingViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnerListingBinding.inflate(inflater, container, false)
        val adapter = CardViewListingAdapter { h, listing ->
            h.binding.listingCardView.setOnClickListener{
                nav.navigate(R.id.ownerListingDetails, bundleOf(
                    "listingID" to listing.id,
                    "fromFragment" to "ownerMYListing"
                )
                )
            }
        }
        binding.rvTenantViewListings.adapter = adapter
        binding.rvTenantViewListings.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listingVM.listingLD().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }
}