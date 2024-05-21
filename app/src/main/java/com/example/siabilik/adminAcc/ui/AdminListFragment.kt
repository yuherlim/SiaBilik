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
import com.example.siabilik.adminAcc.data.AdminViewModel
import com.example.siabilik.adminAcc.util.AdminAdapter
import com.example.siabilik.databinding.FragmentAdminListBinding


class AdminListFragment : Fragment() {

    private lateinit var binding: FragmentAdminListBinding
    private val nav by lazy { findNavController() }

    private val adminVM : AdminViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdminListBinding.inflate(inflater, container, false)


        val adapter = AdminAdapter { h, a ->
            h.binding.root.setOnClickListener { detail(a.id) }
        }
        binding.adminsRV.adapter = adapter
        binding.adminsRV.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        adminVM.getAdminLD().observe(viewLifecycleOwner) {
            binding.numberOfAdmins.text = "${it.size} Admin(s)"

            adapter.submitList(it)
        }


        binding.addAdminButton.setOnClickListener {
            nav.navigate(R.id.adminAddAdminFragment)
        }
        return binding.root
    }

    private fun detail(adminID : String) {
        nav.navigate(R.id.adminDetailsFragment, bundleOf(
            "adminID" to adminID
        ))
    }
}