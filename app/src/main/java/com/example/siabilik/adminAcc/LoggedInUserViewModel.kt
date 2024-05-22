package com.example.siabilik.adminAcc

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.siabilik.data.LoggedInUser


class LoggedInUserViewModel(app : Application) : AndroidViewModel(app) {

    private val _loggedInUserLD = MutableLiveData<LoggedInUser?>()
    val loggedInUserLD: LiveData<LoggedInUser?> get() = _loggedInUserLD

    fun setLoggedInUser(userType: String, userID: String) {
        _loggedInUserLD.value = LoggedInUser(userType, userID)
    }

    fun clearData() {
        _loggedInUserLD.value = null
    }


}