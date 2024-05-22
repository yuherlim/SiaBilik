package com.example.siabilik.tenantAcc.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.siabilik.data.Request
import com.example.siabilik.databinding.TenantRequestItemBinding

class RequestListingAdapter (val fn: (ViewHolder, Request) -> Unit = { _, _ -> }) : ListAdapter<Request, RequestListingAdapter.ViewHolder>(Diff) {

    companion object Diff : DiffUtil.ItemCallback<Request>() {
        override fun areItemsTheSame(a: Request, b: Request) = a.id == b.id
        override fun areContentsTheSame(a: Request, b: Request) = a == b
    }

    class ViewHolder(val binding: TenantRequestItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(TenantRequestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val request = getItem(position)
        holder.binding.txtRequestTitle.text = request.title
        holder.binding.txtRequestDesc.text = request.message.slice(0..60) + "..."
        fn(holder, request)
    }

}