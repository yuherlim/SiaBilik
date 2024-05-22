package com.example.siabilik.adminAcc

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.siabilik.data.LoggedInUser


class LoggedInUserViewModel(app : Application) : AndroidViewModel(app) {

    private val _loggedInUser = MutableLiveData<LoggedInUser>()

    val loggedInUser: LiveData<LoggedInUser> = _loggedInUser

    fun setLoggedInUser(userType: String){
        //loggedInUser.value!!.userID = loggedInUser.userID
        _loggedInUser.value!!.userType = userType

    }

}