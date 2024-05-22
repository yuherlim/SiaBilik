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
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.demo.data.AuthVM
import com.example.siabilik.R
import com.example.siabilik.adminAcc.LoggedInUserViewModel
import com.example.siabilik.data.Tenant
import com.example.siabilik.databinding.FragmentTenantViewRequestsBinding
import com.example.siabilik.tenantAcc.data.RequestViewModel
import com.example.siabilik.tenantAcc.util.RequestListingAdapter


class TenantViewRequestsFragment : Fragment() {
    private lateinit var binding: FragmentTenantViewRequestsBinding
    private val nav by lazy { findNavController() }

    private val requestVM : RequestViewModel by activityViewModels()
    private val userViewModel: LoggedInUserViewModel by activityViewModels()
    private val allUserViewModel: AuthVM by activityViewModels()

    var currentLoggedInTenant: Tenant? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTenantViewRequestsBinding.inflate(inflater, container, false)
        // -----------------------------------------------------------------------------------------

        //clear back stack every time enter top level destination
        nav.popBackStack(R.id.tenantViewRequestsFragment, false)



        // Observe the LiveData
        userViewModel.loggedInUserLD.observe(viewLifecycleOwner, Observer { loggedInUser ->
            when (loggedInUser.userType) {
                "Tenant" -> {
                    currentLoggedInTenant = allUserViewModel.getTenantById(loggedInUser.userID)
                }
            }
        })

        val adapter = RequestListingAdapter { h, request ->
            h.binding.root.setOnClickListener{ detail(request.id) }
        }

        binding.rdRv.adapter = adapter
        binding.rdRv.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))


        requestVM.getRequestLD().observe(viewLifecycleOwner) { it ->
            adapter.submitList(it.filter { it.tenantId == (currentLoggedInTenant?.id ?: "") })
        }

        // -----------------------------------------------------------------------------------------
        return binding.root
    }

    private fun detail(requestId: String) {
        nav.navigate(
            R.id.tenantRequestDetailsFragment, bundleOf(
                "requestId" to requestId,
                //replace hardcoded id with dynamic data
                "tenantId" to (currentLoggedInTenant?.id ?: "")
            )
        )
    }

}