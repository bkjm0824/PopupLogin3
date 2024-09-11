package com.heungjun.popuplogintoken.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.NetworkRepositoryCompany
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModelCompany(
    private val repository: NetworkRepositoryCompany = NetworkRepositoryCompany()
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun login() {
        viewModelScope.launch {
            try {
                val response = repository.loginApi(_email.value, _password.value)
                if (response.result) {
                    _token.value = response.data?.token
                    _errorMessage.value = null
                } else {
                    _token.value = null
                    _errorMessage.value = response.message
                }
            } catch (e: Exception) {
                _token.value = null
                _errorMessage.value = "Login failed: ${e.message}"
            }
        }
    }
}