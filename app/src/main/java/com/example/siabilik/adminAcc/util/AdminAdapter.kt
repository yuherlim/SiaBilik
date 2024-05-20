package com.example.siabilik.adminAcc.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.siabilik.data.Admin
import com.example.siabilik.databinding.AdminListBinding

class AdminAdapter (
    val fn: (ViewHolder, Admin) -> Unit = { _, _ -> }
) : ListAdapter<Admin, AdminAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Admin>() {
        override fun areItemsTheSame(a: Admin, b: Admin) = a.adminID == b.adminID
        override fun areContentsTheSame(a: Admin, b: Admin) = a == b
    }

    class ViewHolder(val binding: AdminListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(AdminListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val admin = getItem(position)

        // TODO : set profile image

        holder.binding.adminName.text = admin.userName

        fn(holder, admin)
    }
}