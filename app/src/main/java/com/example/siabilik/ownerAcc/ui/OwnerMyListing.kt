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
import com.example.siabilik.databinding.FragmentOwnerMyListingBinding
import com.example.siabilik.ownerAcc.data.ListingViewModel
import com.example.siabilik.ownerAcc.util.CardViewListingAdapter

class OwnerMyListing : Fragment() {

    private lateinit var binding: FragmentOwnerMyListingBinding
    private val nav by lazy { findNavController() }
    private val listingVM: ListingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnerMyListingBinding.inflate(inflater, container, false)

        val adapter = CardViewListingAdapter { h, listing ->
            h.binding.listingCardView.setOnClickListener{
                nav.navigate(R.id.ownerMyListingDetails, bundleOf(
                    "listingID" to listing.id,
                    "fromFragment" to "ownerMYListing"
                ))
            }
        }
        binding.rvTenantViewListings.adapter = adapter
        binding.rvTenantViewListings.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listingVM.listingLD().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.addButton.setOnClickListener { nav.navigate(R.id.ownerAddListing) }

        return binding.root
    }

}