package com.example.demo.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.siabilik.data.Admin
import com.example.siabilik.data.Owner
import com.example.siabilik.data.Tenant
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AuthVM (val app: Application) : AndroidViewModel(app) {

    private val TENANT = Firebase.firestore.collection("Tenant")
    private val OWNER = Firebase.firestore.collection("Owner")
    private val ADMIN = Firebase.firestore.collection("Admin")
    private val AdminLD = MutableLiveData<Admin?>()
    private val TenantLD = MutableLiveData<Tenant?>()
    private val OwnerLD = MutableLiveData<Owner?>()
    private val TenantRegLD = MutableLiveData<List<Tenant>>()
    private val OwnerRegLD = MutableLiveData<List<Owner>>()

    private var listener: ListenerRegistration? = null


    init {
        TenantLD.value = null
        OwnerLD.value = null
        listener = TENANT.addSnapshotListener { snap, _ -> TenantRegLD.value = snap?.toObjects() }
        listener = OWNER.addSnapshotListener { snap, _ -> OwnerRegLD.value = snap?.toObjects() }
    }

    // ---------------------------------------------------------------------------------------------

    fun init() = Unit

    fun getUserLD() = TenantLD
    fun getOwnerLD() = OwnerLD
    fun getUserRegLD() = TenantRegLD
    fun getOwnerRegLD() = OwnerRegLD
    fun getAllTenant() = TenantRegLD.value
    fun getAllOwner() = OwnerRegLD.value

    fun getUser() = TenantLD.value
    fun getOwner() = OwnerLD.value
    fun setTenant(tenant: Tenant){
        TENANT.document(tenant.userName).set(tenant)
    }
    fun setOwner(owner: Owner){

        OWNER.document(owner.userName).set(owner)
    }


    // TODO(1): Login
    suspend fun login(username: String, password: String, userType : String): String {


        var admin = ADMIN
        .whereEqualTo("userName", username)
            .whereEqualTo("password", password)
            .get()
            .await()
            .toObjects<Admin>()
            .firstOrNull()

        if (admin!=null) {
            listener?.remove()
            listener = ADMIN.document(admin.userName).addSnapshotListener { snap, _ ->
                TenantLD.value = snap?.toObject()
            }
            return "admin"
        }




        if(userType == "Tenant") {

            val tenant = TENANT
                .whereEqualTo("userName", username)
                .whereEqualTo("password", password)
                .get()
                .await()
                .toObjects<Tenant>()
                .firstOrNull() ?: return "NA"


            listener?.remove()
            listener = TENANT.document(tenant.userName).addSnapshotListener { snap, _ ->
                TenantLD.value = snap?.toObject()
            }
        return "Tenant"
        }else{
            val owner = OWNER
                .whereEqualTo("userName", username)
                .whereEqualTo("password", password)
                .get()
                .await()
                .toObjects<Owner>()
                .firstOrNull() ?: return "NA"

            // TODO(1B): Setup snapshot listener
            //           Update live data -> user
            listener?.remove()
            listener = OWNER.document(owner.userName).addSnapshotListener { snap, _ ->
                TenantLD.value = snap?.toObject()
            }
            return "Owner"
        }



     }


        // TODO(2): Logout
        fun logout() {
            // TODO(2A): Remove snapshot listener
            //           Update live data -> null
            listener?.remove()
            TenantLD.value = null


            // [OR] getPreferences().edit().clear().apply()
            // [OR] app.deleteSharedPreferences("AUTH")
        }

    suspend fun register(userType: String) {
        when(userType){
            "Tenant" -> validateTenant(Tenant())
            "Owner" -> validateOwner(Owner())
        }
    }

    fun validateTenant(tenant: Tenant){
        val regexEmail = Regex("""^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$""")
        val regexPhoneNumber = Regex("^0\\d{1,2}-?\\d{7,8}|01[0-46-9]-?\\d{7,8}\$")
        var e = ""
            e += if (tenant.userName == "") "- Username required.\n"
            else if (tenant.userName.length < 3) "- Username too short (min 3 chars).\n"
            else if (tenant.userName.length > 20) "- Username too long (max 20 chars).\n"
            else ""
            e += if (tenant.email == "") "- Email required.\n"
            else if (!tenant.email.matches(regexEmail)) "- Email format invalid.\n"
            else if (tenant.email.length > 100) "- Email too long (max 100 chars).\n"
            else ""
            e += if (tenant.phoneNumber == "") "- Phone Number required.\n"
            else if (!tenant.phoneNumber.matches(regexPhoneNumber)) "- Phone Number format invalid.\n"
            else if (tenant.phoneNumber.length > 100) "- Phone Number too long (max 100 chars).\n"
            else ""
            e += if (tenant.password == "") "- Password required.\n"
            else if (tenant.password.length < 5) "- Password too short (min 5 chars).\n"
            else if (tenant.password.length > 100) "- Password too long (max 100 chars).\n"
            else ""

    }

    fun validateOwner(owner: Owner){
        val regexEmail = Regex("""^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$""")
        val regexPhoneNumber = Regex("^0\\d{1,2}-?\\d{7,8}|01[0-46-9]-?\\d{7,8}\$")
        var e = ""
        e += if (owner.userName == "") "- Username required.\n"
        else if (owner.userName.length < 3) "- Username too short (min 3 chars).\n"
        else if (owner.userName.length > 20) "- Username too long (max 100 chars).\n"
        else ""
        if (owner.email == "") "- Email required.\n"
        else if (!owner.email.matches(regexEmail)) "- Email format invalid.\n"
        else if (owner.email.length > 100) "- Email too long (max 20 chars).\n"
        else ""
        e += if (owner.phoneNumber == "") "- Phone Number required.\n"
        else if (!owner.phoneNumber.matches(regexPhoneNumber)) "- Phone Number format invalid.\n"
        else if (owner.phoneNumber.length > 100) "- Phone Number too long (max 100 chars).\n"
        else ""
        e += if (owner.password == "") "- Password required.\n"
        else if (owner.password.length < 5) "- Password too short (min 5 chars).\n"
        else if (owner.password.length > 100) "- Password too long (max 100 chars).\n"
        else ""
    }


}
