package com.example.siabilik.adminAcc.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.siabilik.databinding.AdminListingListBinding
import com.example.siabilik.ownerAcc.ui.Listing

class ListingAdapter (
    val fn: (ViewHolder, Listing) -> Unit = { _, _ -> }
) : ListAdapter<Listing, ListingAdapter.ViewHolder>(DiffCallback)){

    companion object DiffCallback : DiffUtil.ItemCallback<Listing>() {
        override fun areItemsTheSame(a: Listing, b: Listing) = a.id == b.id
        override fun areContentsTheSame(a: Listing, b: Listing) = a == b
    }

    class ViewHolder(val binding: AdminListingListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(AdminListingListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listing = getItem(position)


        fn(holder, listing)
    }
}