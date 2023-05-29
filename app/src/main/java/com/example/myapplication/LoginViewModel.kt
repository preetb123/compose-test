package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    val usernameState = MutableLiveData<String>()
    val passwordState = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    fun onUsernameChanged(newText: String){
    }

    fun onPasswordChanged(newText: String){
        passwordState.postValue(newText)
    }

    fun onLoginButtonClick(){
        println(usernameState.value + ", " + passwordState.value)
        loading.postValue(true)
    }
}