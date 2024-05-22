package com.example.demo.data

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Email
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

class AuthVM(val app: Application) : AndroidViewModel(app) {

    private val TENANT = Firebase.firestore.collection("Tenant")
    private val OWNER = Firebase.firestore.collection("Owner")
    private val ADMIN = Firebase.firestore.collection("Admin")
    private val AdminLD = MutableLiveData<Admin?>()
    private val TenantLD = MutableLiveData<Tenant?>()
    private val OwnerLD = MutableLiveData<Owner?>()
    private val TenantRegLD = MutableLiveData<List<Tenant>>()
    private val OwnerRegLD = MutableLiveData<List<Owner>>()
    private val AdminRegLD = MutableLiveData<List<Admin>>()
    private var listener: ListenerRegistration? = null
    private var ownerListener: ListenerRegistration? = null
    private var tenantlistener: ListenerRegistration? = null
    private var adminlistener: ListenerRegistration? = null

    init {
        TenantLD.value = null
        OwnerLD.value = null
        ownerListener = TENANT.addSnapshotListener { snap, _ -> TenantRegLD.value = snap?.toObjects() }
        tenantlistener = OWNER.addSnapshotListener { snap, _ -> OwnerRegLD.value = snap?.toObjects() }
        adminlistener = ADMIN.addSnapshotListener { snap, _ -> AdminRegLD.value = snap?.toObjects() }
    }

    // ---------------------------------------------------------------------------------------------

    fun init() = Unit

    fun getUserRegLD() = TenantRegLD
    fun getOwnerRegLD() = OwnerRegLD
    fun getAllTenant() = TenantRegLD.value
    fun getAllOwner() = OwnerRegLD.value
    fun getUser() = TenantLD.value
    fun getOwner() = OwnerLD.value

    fun getOwnerById(id: String) = OwnerRegLD.value?.find { it.id == id }

    fun getTenantById(id: String) = TenantRegLD.value?.find { it.id == id }

    fun getAdminById(id: String) = AdminRegLD.value?.find { it.id == id }

    fun getOwnerByEmail(email: String) = OwnerRegLD.value?.find { it.email == email }

    fun getTenantByEmail(email: String) = TenantRegLD.value?.find { it.email == email }

    fun getLatestTenant(): Tenant? {
        return TenantRegLD.value?.lastOrNull()
    }

    fun getLatestOwner(): Owner? {
        return OwnerRegLD.value?.lastOrNull()
    }

    fun setTenant(tenant: Tenant) {
        TENANT.document(tenant.userName).set(tenant)
    }

    fun setOwner(owner: Owner) {
        OWNER.document(owner.userName).set(owner)
    }

    fun addOwner(owner: Owner){
        var latestOwner= getLatestOwner()
        var id : Int = 0

        if (latestOwner != null) {
            id = latestOwner.id.removePrefix("Owner").toIntOrNull()!!
            id = id.plus(1)
        }

        var newOwnerID = "Owner${id.toString().padStart(3, '0')}"
        owner.id=newOwnerID
        OWNER.document(owner.id).set(owner)
    }

    fun addTenant(tenant: Tenant){
        var latestTenant = getLatestTenant()
        var id : Int = 0

        if (latestTenant != null) {
            id = latestTenant.id.removePrefix("Tenant").toIntOrNull()!!
            id = id.plus(1)
        }

        var newTenantID = "Tenant${id.toString().padStart(3, '0')}"
        tenant.id = newTenantID
        TENANT.document(tenant.id).set(tenant)
    }

    suspend fun login(username: String, password: String, userType: String):List<String> {
        var loginResultList = MutableList(2){""}

        var admin = ADMIN
            .whereEqualTo("userName", username)
            .whereEqualTo("password", password)
            .get()
            .await()
            .toObjects<Admin>()
            .firstOrNull()

        if (admin != null) {
            loginResultList[0] = "Admin"
            loginResultList[1] = admin.id
            return loginResultList
        }


        if (userType == "Tenant") {

            val tenant = TENANT
                .whereEqualTo("userName", username)
                .whereEqualTo("password", password)
                .get()
                .await()
                .toObjects<Tenant>()
                .firstOrNull()

            if (tenant != null) {
                loginResultList[0] = "Tenant"
                loginResultList[1] = tenant.id
                return loginResultList
            }
            loginResultList[0] = "NA"
            return loginResultList
        } else {
            val owner = OWNER
                .whereEqualTo("userName", username)
                .whereEqualTo("password", password)
                .get()
                .await()
                .toObjects<Owner>()
                .firstOrNull()

            if (owner != null) {
                loginResultList[0] = "Owner"
                loginResultList[1] = owner.id
                return loginResultList
            }
            loginResultList[0] = "NA"
            return loginResultList
        }
    }

    fun logout() {
        listener?.remove()
        TenantLD.value = null

    }

    /*suspend fun register(userType: String) {
        when (userType) {
            "Tenant" -> validateTenant(Tenant())
            "Owner" -> validateOwner(Owner())
        }
    }

    fun validateTenant(tenant: Tenant): String {
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

        return e
    }

    fun validateOwner(owner: Owner): String {
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
        return e
    }*/

    suspend fun reset(userType: String, email: String): String {
        if (userType == "Tenant") {

            val tenant = TENANT
                .whereEqualTo("email", email)
                .get()
                .await()
                .toObjects<Tenant>()
                .firstOrNull() ?: return "NA"


            listener?.remove()
            listener = TENANT.document(tenant.email).addSnapshotListener { snap, _ ->
                TenantLD.value = snap?.toObject()
            }
            return "Tenant"
        } else {
            val owner = OWNER
                .whereEqualTo("email", email)
                .get()
                .await()
                .toObjects<Owner>()
                .firstOrNull() ?: return "NA"


            listener?.remove()
            listener = OWNER.document(owner.email).addSnapshotListener { snap, _ ->
                OwnerLD.value = snap?.toObject()
            }
            return "Owner"
        }
        return "NA"
    }


}
