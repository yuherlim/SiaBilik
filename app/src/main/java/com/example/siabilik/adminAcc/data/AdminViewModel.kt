package com.example.siabilik.adminAcc.data

import android.app.Application
import android.media.CamcorderProfile.getAll
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.siabilik.Listing
import com.example.siabilik.data.ADMIN
import com.example.siabilik.data.Admin
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObjects

class AdminViewModel(app : Application) : AndroidViewModel(app) {

    private val adminLD = MutableLiveData<List<Admin>>(emptyList())
    private var listener : ListenerRegistration? = null

    init {
        listener = ADMIN.addSnapshotListener { snap, _ ->
            adminLD.value = snap?.toObjects()
        }
    }



}
