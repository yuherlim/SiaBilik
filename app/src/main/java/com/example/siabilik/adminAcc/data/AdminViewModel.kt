package com.example.siabilik.adminAcc.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.siabilik.data.ADMIN
import com.example.siabilik.data.Admin
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObjects

class  AdminViewModel(app : Application) : AndroidViewModel(app) {

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

    fun validate(a: Admin, confirmPassword: String): String {
        //validation to do
        var e = ""

        e += if (a.userName.contains(" ")) "- Name cannot contain spaces.\n"
        else if (a.userName == "") "- Name is required.\n"
        else if (a.userName.length < 3) "- Name is too short (at least 3 letters).\n"
        else if (a.userName.length > 100) "- Name is too long (at most 100 letters).\n"
        else ""

        e += if (a.email == "") "- Email is required.\n"
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(a.email).matches()) "- Invalid email format.\n"
        else if (a.email.length > 100) "- Email is too long (at most 100 characters).\n"
        else ""

        e += if (a.adminPhoto.toBytes().isEmpty()) "- Photo is required.\n"
        else ""

        if(!a.password.equals(confirmPassword)){
            e += "- Password must be same with confirmPassword.\n"
        }else {
            e += if (a.password == "") "- Password is required.\n"
            else if (a.password.length < 8) "- Password is too short (at least 8 characters).\n"
            else if (a.password.length > 100) "- Password is too long (at most 100 characters).\n"
            else ""

            if (!a.password.matches(".*[A-Z].*".toRegex()))
                e += "- Password must contain at least one uppercase letter.\n"
            if (!a.password.matches(".*[a-z].*".toRegex()))
                e += "- Password must contain at least one lowercase letter.\n"
            if (!a.password.matches(".*[0-9].*".toRegex()))
                e += "- Password must contain at least one digit.\n"
            if (!a.password.matches(".*[!@#\$%^&*(),.?'\";:{}|<>].*".toRegex()))
                e += "- Password must contain at leat one symbol.\n"
        }

        e += if (a.phoneNumber.isEmpty() || a.phoneNumber.length < 10 || a.phoneNumber.length > 11
            || !a.phoneNumber.startsWith("0")) "- Invalid phone number format. Must start with 0 and be 10 or 11 digits long.\n"
        else ""

        return e
    }


}
