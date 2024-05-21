package com.example.siabilik.adminAcc.util

import android.accounts.Account
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.siabilik.data.Tenant
import com.example.siabilik.databinding.AdminAccountListBinding
import com.example.siabilik.setImageBlob

class AccountAdapter (
    val fn: (ViewHolder, Tenant) -> Unit = { _, _ -> }
) : ListAdapter<Tenant, AccountAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Tenant>() {
        override fun areItemsTheSame(a: Tenant, b: Tenant) = a.id == b.id
        override fun areContentsTheSame(a: Tenant, b: Tenant) = a == b
    }

    class ViewHolder(val binding: AdminAccountListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(AdminAccountListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tenant = getItem(position)

        holder.binding.listingImage.setImageBlob(tenant.profilePic)
        holder.binding.lblAccountName.text = tenant.userName

        fn(holder, tenant)
    }


}