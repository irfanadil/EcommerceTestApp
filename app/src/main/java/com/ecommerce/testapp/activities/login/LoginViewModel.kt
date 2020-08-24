package com.ecommerce.testapp

import android.util.Log
import android.util.Patterns
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @ViewModelInject constructor(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponseResult>()
    val loginResponseResult: LiveData<LoginResponseResult> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            var bool = false
            val userListResult = loginRepository.fetchUserList()
            if (userListResult is Result.Success) {
                val userList = userListResult.data.userList
                if (userList != null) {
                    for (user in userList) {
                        if (user.userName == username && user.password == password) {
                            bool = true
                            break
                        }
                    }
                }
            }
            if (bool) {
                val result = loginRepository.login(username, password)
                Log.e("result - ", result.toString())
                if (result is Result.Success)
                    _loginResult.value =
                        LoginResponseResult(success = LoginResponse(token = result.data.token))
                else
                    _loginResult.value = LoginResponseResult(error = R.string.login_failed)
            }
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}