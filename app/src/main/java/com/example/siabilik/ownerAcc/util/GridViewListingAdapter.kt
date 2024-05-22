package com.example.siabilik.ownerAcc.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.siabilik.cropToBlob
import com.example.siabilik.data.Listing
import com.example.siabilik.databinding.OwnerCarditemBinding
import com.example.siabilik.databinding.OwnerGridviewitemBinding
import com.example.siabilik.setImageBlob

class GridViewListingAdapter (val fn: (ViewHolder, Listing) -> Unit = { _, _ -> }) : ListAdapter<Listing, GridViewListingAdapter.ViewHolder>(Diff) {

    companion object Diff : DiffUtil.ItemCallback<Listing>() {
        override fun areItemsTheSame(a: Listing, b: Listing) = a.id == b.id
        override fun areContentsTheSame(a: Listing, b: Listing) = a == b
    }

    class ViewHolder(val binding: OwnerGridviewitemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(OwnerGridviewitemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listing = getItem(position)
        holder.binding.title.text = listing.title
        val featureList = listing.features.split(',')
        var featureString = "Features:\n"
        featureList.forEach {
            featureString += "- ${it.trim()}\n"
        }
        holder.binding.features.text = featureString
        holder.binding.rental.text = String.format("RM %.2f",listing.rental.toDouble())
        holder.binding.propertyPhoto.setImageBlob(listing.propertyPhoto)
        var reSizedImage = holder.binding.propertyPhoto.cropToBlob(300,300)
        holder.binding.propertyPhoto.setImageBlob(reSizedImage)
        fn(holder, listing)
    }

}