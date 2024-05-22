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
import com.example.siabilik.adminAcc.data.AccountApproveViewModel
import com.example.siabilik.adminAcc.util.AccountAdapter
import com.example.siabilik.databinding.FragmentAdminAccountApproveBinding

class AdminAccountApproveFragment : Fragment() {

    private lateinit var binding: FragmentAdminAccountApproveBinding
    private val nav by lazy { findNavController() }

    private val accountVM : AccountApproveViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminAccountApproveBinding.inflate(inflater, container, false)

        val adapter = AccountAdapter { h, a ->
            h.binding.root.setOnClickListener { detail(a.id) }
        }
        binding.accountApproveRV.adapter = adapter
        binding.accountApproveRV.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        accountVM.getFilteredAccountLD().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        return binding.root
    }

    private fun detail(accountID : String) {
        nav.navigate(
            R.id.adminAccountApproveDetailsFragment, bundleOf(
            "accountID" to accountID
        )
        )
    }

}