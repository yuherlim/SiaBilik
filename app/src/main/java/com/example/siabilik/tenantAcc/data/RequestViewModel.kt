package com.example.siabilik.tenantAcc.data
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.siabilik.data.REQUEST
import com.example.siabilik.data.Request
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObjects


class RequestViewModel(app: Application) : AndroidViewModel(app) {
    // TODO: Live data
    private val requestLD = MutableLiveData<List<Request>>(emptyList())
    private var listener : ListenerRegistration? = null

    init {
        listener = REQUEST.addSnapshotListener { snap, _ ->
            requestLD.value = snap?.toObjects()
        }
    }

    override fun onCleared() {
        listener?.remove()
    }

    fun init() = Unit

    fun getRequestLD() = requestLD

    fun getAll() = requestLD.value ?: emptyList()

    fun get(id: String) = getAll().find { it.id == id }

    fun getLatestRequest(): Request? {
        return requestLD.value?.lastOrNull()
    }

    fun set(request: Request) {
        REQUEST.document(request.id).set(request)
    }

    fun validate(request: Request): String {
        //validation to do
        var e = ""

        e += if (request.title.isEmpty()) "- Title cannot be empty.\n" else ""

        e += if (request.message.isEmpty()) "- Message cannot be empty.\n" else ""

        return e
    }


}