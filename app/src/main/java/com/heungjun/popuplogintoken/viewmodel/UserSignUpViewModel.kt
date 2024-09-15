package com.heungjun.popuplogintoken.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.SignUpUserRepo
import com.heungjun.popuplogintoken.model.SignUpUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserSignUpViewModel : ViewModel() {

    private val _signUpUser = MutableStateFlow(SignUpUser(
        birth = "", categories = emptyList(), detailAddress = "", email = "",
        gender = "", mapx = "", mapy = "", nickname = "", password = "",
        phone = "", postcode = "", username = ""
    ))

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess: StateFlow<Boolean> = _signUpSuccess

    // New StateFlow to expose the username
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    fun onSignUpUserChange(newSignUpUser: SignUpUser) {
        _signUpUser.value = newSignUpUser
    }

    fun signUp() {
        viewModelScope.launch {
            try {
                val response = SignUpUserRepo.signUpUser(_signUpUser.value)
                if (response?.result == true) {
                    _signUpSuccess.value = true
                    _errorMessage.value = null
                    _username.value = _signUpUser.value.username // Store the username after successful sign-up
                } else {
                    _errorMessage.value = response?.message ?: "Unknown error"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Sign up failed: ${e.message}"
            }
        }
    }
}
