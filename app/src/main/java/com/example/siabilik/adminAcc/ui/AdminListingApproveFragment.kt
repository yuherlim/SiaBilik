package com.example.siabilik.adminAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.siabilik.R
import com.example.siabilik.adminAcc.data.ListingApproveViewModel
import com.example.siabilik.adminAcc.util.ListingAdapter
import com.example.siabilik.databinding.FragmentAdminListingApproveBinding

class AdminListingApproveFragment : Fragment() {

    private lateinit var binding: FragmentAdminListingApproveBinding
    private val nav by lazy { findNavController() }


    private val listingVM : ListingApproveViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminListingApproveBinding.inflate(inflater, container, false)

        val adapter = ListingAdapter { h, l ->
            h.binding.root.setOnClickListener { detail(l.id) }
        }
        binding.listingApproveRV.adapter = adapter
        binding.listingApproveRV.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        listingVM.getFilteredListingLD().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    private fun detail(listingID : String) {
        nav.navigate(
            R.id.adminListingApproveDetailsFragment, bundleOf(
            "listingID" to listingID
        )
        )
    }
}