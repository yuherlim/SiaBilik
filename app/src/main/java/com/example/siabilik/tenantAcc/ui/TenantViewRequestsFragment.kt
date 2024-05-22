package com.example.siabilik.tenantAcc.ui

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
import com.example.siabilik.databinding.FragmentTenantViewRequestsBinding
import com.example.siabilik.tenantAcc.data.RequestViewModel
import com.example.siabilik.tenantAcc.util.RequestListingAdapter


class TenantViewRequestsFragment : Fragment() {
    private lateinit var binding: FragmentTenantViewRequestsBinding
    private val nav by lazy { findNavController() }
    private val requestVM : RequestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTenantViewRequestsBinding.inflate(inflater, container, false)
        // -----------------------------------------------------------------------------------------

        //clear back stack every time enter top level destination
        nav.popBackStack(R.id.tenantViewRequestsFragment, false)

        val adapter = RequestListingAdapter { h, request ->
            h.binding.root.setOnClickListener{ detail(request.id) }
        }

        binding.rdRv.adapter = adapter
        binding.rdRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))


        //replace hardcoded id with dynamic data
        requestVM.getRequestLD().observe(viewLifecycleOwner) { it ->
            adapter.submitList(it.filter { it.tenantId == "Tenant001" })
        }

        // -----------------------------------------------------------------------------------------
        return binding.root
    }

    private fun detail(requestId: String) {
        nav.navigate(
            R.id.tenantRequestDetailsFragment, bundleOf(
                "requestId" to requestId,
                //replace hardcoded id with dynamic data
                "tenantId" to "Tenant001"
            )
        )
    }

}