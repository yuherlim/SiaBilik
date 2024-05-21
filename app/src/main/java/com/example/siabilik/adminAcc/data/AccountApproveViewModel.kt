package com.example.siabilik.adminAcc.data
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.siabilik.ownerAcc.ui.Listing
import com.google.firebase.firestore.ListenerRegistration

class AccountApproveViewModel(app : Application) : AndroidViewModel(app) {

    private val accountApproveLD = MutableLiveData<List<Listing>>(emptyList())
    private var listener : ListenerRegistration? = null

    init {

    }

    // TODO: Init block
    init {

    }


}


