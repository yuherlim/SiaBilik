package com.example.siabilik.adminAcc.data
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.siabilik.data.TENANT
import com.example.siabilik.data.Tenant
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObjects

class AccountApproveViewModel(app : Application) : AndroidViewModel(app) {

    private val accountApproveLD = MutableLiveData<List<Tenant>>(emptyList())
    private var listener : ListenerRegistration? = null

    init {
        listener = TENANT.addSnapshotListener { snap, _ ->
            accountApproveLD.value = snap?.toObjects()
        }
    }

    fun getAccountApproveLD() = accountApproveLD






}


