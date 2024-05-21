package com.example.siabilik.adminAcc.data
import android.accounts.Account
import android.app.Application
import android.media.CamcorderProfile.getAll
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.siabilik.data.Listing
import com.example.siabilik.data.TENANT
import com.example.siabilik.data.Tenant
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObjects

class AccountApproveViewModel(app : Application) : AndroidViewModel(app) {

    private val accountApproveLD = MutableLiveData<List<Tenant>>(emptyList())
    private val resultLD = MutableLiveData<List<Tenant>>()

    private var listener : ListenerRegistration? = null

    init {
        listener = TENANT.addSnapshotListener { snap, _ ->
            accountApproveLD.value = snap?.toObjects()
            getFilteredAccountLD()
        }
    }

    fun getFilteredAccountLD(): LiveData<List<Tenant>> {
        var list = getAll()
        list = list.filter {
            it.verificationStatus == "Pending"
        }
        resultLD.value = list
        return resultLD
    }

    fun get(id: String) = accountApproveLD.value?.find { it.id == id }

    fun getAll() = accountApproveLD.value ?: emptyList()

    fun set(t : Tenant) {
        TENANT.document(t.id).set(t)
    }


}


