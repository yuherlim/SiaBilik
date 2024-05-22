package com.example.siabilik.tenantAcc.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.siabilik.R


class TenantViewRequestsFragment : Fragment() {
    private val nav by lazy { findNavController() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //clear back stack every time enter top level destination
        nav.popBackStack(R.id.tenantViewRequestsFragment, false)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tenant_view_requests, container, false)
    }

}