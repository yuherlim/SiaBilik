package com.example.siabilik.ownerAcc.data
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.siabilik.data.LISTING
import com.example.siabilik.data.Listing
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObjects


class ListingViewModel(app: Application) : AndroidViewModel(app) {
    // TODO: Live data
    private val listingLD = MutableLiveData<List<Listing>>(emptyList())
    private var listener : ListenerRegistration? = null

    init {
        listener = LISTING.addSnapshotListener { snap, _ ->
            listingLD.value = snap?.toObjects()
            updateResult()
        }
    }

    override fun onCleared() {
        listener?.remove()
    }

    fun init() = Unit

    fun listingLD() = listingLD

    fun getAll() = listingLD.value ?: emptyList()

    fun get(id: String) = getAll().find { it.id == id }

    // Get all list by status
    fun getAllByStatus(status: String) = getAll().filter { it.status == "status" }

    // Get all list by status
    fun getAllByApprovalStatus(status: String) = getAll().filter { it.approvalStatus == "status" }

    private val resultLD = MutableLiveData<List<Listing>>()
    private var name = ""
    private var categoryId = ""
    private var field = ""
    private var status=""
    private var reverse = false

    fun getResultLD() = resultLD

    fun search(title: String) {
        this.name = name
        updateResult()
    }

    fun filter(status: String) {
        this.status = status
        updateResult()
    }

    fun sort(field: String, reverse: Boolean) {
        this.field = field
        this.reverse = reverse
        updateResult()
    }

    fun updateResult() {
        var list = getAll()

        // TODO(12A): Search by name, filter by categoryId
        list = list.filter {
            it.title.contains(name, true) &&
                    (it.status == categoryId || categoryId == "")
        }

        // TODO(12B): Sort by field
        list = when (field) {
            "Date" -> list.sortedBy { it.id }
            else   -> list
        }

        // TODO(12C): Reverse (descending order)
        if (reverse) list = list.reversed()

        resultLD.value = list
    }


}