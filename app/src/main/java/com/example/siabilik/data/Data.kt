package com.example.siabilik.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.firestore

data class Admin(
    @DocumentId
    var id : String = "",
    var email : String = "",
    var password : String = "",
    var phoneNumber : String = "",
    var role : String = "",
    var userName : String = ""
)





val ADMIN = Firebase.firestore.collection("Admin")