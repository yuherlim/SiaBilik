package com.example.siabilik.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.firestore
import java.sql.ClientInfoStatus

data class Admin (
    @DocumentId
    var id : String = "",
    var email : String = "",
    var password : String = "",
    var phoneNumber : String = "",
    var userName : String = "",
    var adminPhoto : Blob = Blob.fromBytes(ByteArray(0))
)

val ADMIN = Firebase.firestore.collection("Admin")


data class Owner(
    @DocumentId
    var ownerID : String = "",
    var email : String = "",
    var password : String = "",
    var phoneNumber : String = "",
    var profilePic : String = "",
    var userName : String = ""
)


val OWNER = Firebase.firestore.collection("Owner")

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


data class Listing(
    @DocumentId
    var id : String = "",
    var title: String ="",
    var status: String ="",
    var descrription: String ="",
    var address : String = "",
    var approvalStatus : String = "",
    var features : String = "",
    var userID: String = "",
    var rental: Double = 0.0
)

val LISTING = Firebase.firestore.collection("Owner")


