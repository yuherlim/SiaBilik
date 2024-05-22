package com.example.siabilik.adminAcc.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.siabilik.data.LISTING
import com.example.siabilik.data.Listing
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObjects

class ListingApproveViewModel(app : Application) : AndroidViewModel(app) {

    private val listingLD = MutableLiveData<List<Listing>>(emptyList())
    private val resultLD = MutableLiveData<List<Listing>>()

    private var listener : ListenerRegistration? = null

    init {
        listener = LISTING.addSnapshotListener { snap, _ ->
            listingLD.value = snap?.toObjects()
            getFilteredListingLD()
        }
    }

    fun init() = Unit
    fun getFilteredListingLD(): LiveData<List<Listing>> {
        var list = getAll()
        list = list.filter {
            it.approvalStatus == "Pending"
        }
        resultLD.value = list
        return resultLD
    }


    fun get(id: String) = listingLD.value?.find { it.id == id }

    fun getAll() = listingLD.value ?: emptyList()

    fun set(l : Listing) {
        LISTING.document(l.id).set(l)
    }
}