package com.example.siabilik.adminAcc.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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

    fun init() = Unit

    fun get(id: String) = adminLD.value?.find { it.adminID == id }

    fun delete(id : String){
        ADMIN.document(id).delete()
    }

    fun set(a : Admin) {
        ADMIN.document(a.adminID).set(a)
    }



}
