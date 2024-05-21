package com.example.siabilik.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.firestore
import java.sql.ClientInfoStatus

data class Admin (
    @DocumentId
    var adminID : String = "",
    var email : String = "",
    var password : String = "",
    var phoneNumber : String = "",
    var profilePic : String = "",
    var role : String = "",
    var userID : String = "",
    var userName : String = ""
)

data class Tenant (
    @DocumentId
    var id: String = "",
    var email : String = "",
    var password : String = "",
    var phoneNumber : String = "",
    var profilePic : Blob = Blob.fromBytes(ByteArray(0)),
    var userName : String = "",
    var verificationStatus: String = "",
    var aboutDescription: String = "",
    var selfiePhoto : Blob = Blob.fromBytes(ByteArray(0)),
)

data class Owner (
    @DocumentId
    var id: String = "",
    var email : String = "",
    var password : String = "",
    var phoneNumber : String = "",
    var profilePic : Blob = Blob.fromBytes(ByteArray(0)),
    var userName : String = "",
    var aboutDescription: String = "",

)




val ADMIN = Firebase.firestore.collection("Admin")