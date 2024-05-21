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

    fun getAdminLD() = adminLD

    fun get(id: String) = adminLD.value?.find { it.id == id }

    fun delete(id : String){
        ADMIN.document(id).delete()
    }

    fun getLatestAdmin(): Admin? {
        return adminLD.value?.lastOrNull()
    }

    fun set(a : Admin) {
        ADMIN.document(a.id).set(a)
    }

    fun validate(a: Admin, insert: Boolean = true): String {
        //validation to do
        var e = ""

        return e
    }


}
