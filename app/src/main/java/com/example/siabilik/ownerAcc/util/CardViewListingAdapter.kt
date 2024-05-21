package com.example.siabilik.ownerAcc.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.siabilik.data.Listing
import com.example.siabilik.databinding.OwnerCarditemBinding
import com.example.siabilik.setImageBlob
import java.text.SimpleDateFormat

class CardViewListingAdapter (val fn: (ViewHolder, Listing) -> Unit = { _, _ -> }) : ListAdapter<Listing, CardViewListingAdapter.ViewHolder>(Diff) {

    companion object Diff : DiffUtil.ItemCallback<Listing>() {
        override fun areItemsTheSame(a: Listing, b: Listing) = a.id == b.id
        override fun areContentsTheSame(a: Listing, b: Listing) = a == b
    }

    class ViewHolder(val binding: OwnerCarditemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(OwnerCarditemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listing = getItem(position)
        holder.binding.title.text = listing.title
        holder.binding.Approvalstatus.text = listing.approvalStatus
        holder.binding.features.text = listing.features
        holder.binding.Listingstatus.text = listing.status
        holder.binding.rental.text = String.format("RM %.2f",listing.rental.toDouble())
        //holder.binding.dateCreated.text =  SimpleDateFormat("dd-mm-yyyy").format(listing.createdDateTime)
        //holder.binding.propertyPhoto.setImageBlob(listing.propertyPhoto)
        fn(holder, listing)
    }



}