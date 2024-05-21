package com.example.siabilik.adminAcc.util

import android.accounts.Account
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.siabilik.data.Admin
import com.example.siabilik.data.Tenant
import com.example.siabilik.databinding.AdminListBinding
import com.example.siabilik.databinding.AdminListingListBinding
import com.example.siabilik.setImageBlob

class AccountAdapter (
    val fn: (ViewHolder, Tenant) -> Unit = { _, _ -> }
) : ListAdapter<Tenant, AccountAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Tenant>() {
        override fun areItemsTheSame(a: Tenant, b: Tenant) = a.id == b.id
        override fun areContentsTheSame(a: Tenant, b: Tenant) = a == b
    }

    class ViewHolder(val binding: AdminListingListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(AdminListingListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tenant = getItem(position)

        // TODO : set profile image

        fn(holder, tenant)
    }


}