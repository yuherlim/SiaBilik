package com.example.siabilik.tenantAcc.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.siabilik.data.Listing
import com.example.siabilik.databinding.TenantListingItemBinding
import com.example.siabilik.setImageBlob

class GridViewListingAdapter (val fn: (ViewHolder, Listing) -> Unit = { _, _ -> }) : ListAdapter<Listing, GridViewListingAdapter.ViewHolder>(Diff) {

    companion object Diff : DiffUtil.ItemCallback<Listing>() {
        override fun areItemsTheSame(a: Listing, b: Listing) = a.id == b.id
        override fun areContentsTheSame(a: Listing, b: Listing) = a == b
    }

    class ViewHolder(val binding: TenantListingItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(TenantListingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listing = getItem(position)
        holder.binding.txtTenantListingTitle.text = listing.title
        holder.binding.txtTenantListingRental.text = String.format("RM %.2f",listing.rental.toString().toFloat())
        //Photo
        holder.binding.imgTenantListing.setImageBlob(listing.propertyPhoto)
        fn(holder, listing)
    }

}