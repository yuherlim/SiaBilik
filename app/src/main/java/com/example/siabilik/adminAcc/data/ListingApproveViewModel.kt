package com.example.siabilik.adminAcc.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.siabilik.data.LISTING
import com.example.siabilik.data.Listing
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObjects

class ListingApproveViewModel(app : Application) : AndroidViewModel(app) {

    private val listingLD = MutableLiveData<List<Listing>>(emptyList())
    private var listener : ListenerRegistration? = null

    init {
        listener = LISTING.addSnapshotListener { snap, _ ->
            listingLD.value = snap?.toObjects()
        }
    }

    fun getListingLD() = listingLD


}