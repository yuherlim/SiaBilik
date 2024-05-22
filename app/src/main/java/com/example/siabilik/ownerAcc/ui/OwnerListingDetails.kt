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
import com.example.siabilik.ownerAcc.data.ListingViewModel
import com.example.siabilik.ownerAcc.util.CardViewListingAdapter
import com.example.siabilik.setImageBlob


class OwnerListingDetails : Fragment() {
    private lateinit var binding: FragmentOwnerListingDetailsBinding
    private val nav by lazy { findNavController() }
    private val listingVM: ListingViewModel by activityViewModels()
    private val listingID by lazy { arguments?.getString("listingID") ?: "" }
    private val fromFragment by lazy { arguments?.getString("fromFragment") ?: "" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnerListingDetailsBinding.inflate(inflater, container, false)
        bindData()
        return binding.root
    }

    fun bindData() {
        var selectedListing = listingVM.getListingById(listingID)
        if (selectedListing != null) {
            binding.title.text = selectedListing.title
            binding.features.text = selectedListing.features
            binding.rental.text = String.format("RM %.2f", selectedListing.rental.toDouble())
            binding.propertyPhoto.setImageBlob(selectedListing.propertyPhoto)

        }
    }
}