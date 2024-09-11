package com.heungjun.popuplogintoken.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.SignUpCompRepo
import com.heungjun.popuplogintoken.model.ComSignUP
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CompanySignUpViewModel : ViewModel() {

    private val _companyName = MutableStateFlow("")
    val companyName: StateFlow<String> = _companyName

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _managerName = MutableStateFlow("")
    val managerName: StateFlow<String> = _managerName

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address

    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess: StateFlow<Boolean> = _signUpSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun onCompanyNameChange(newCompanyName: String) {
        _companyName.value = newCompanyName
    }

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onManagerNameChange(newManagerName: String) {
        _managerName.value = newManagerName
    }

    fun onAddressChange(newAddress: String) {
        _address.value = newAddress
    }

    fun signUp() {
        viewModelScope.launch {
            try {
                val comSignUp = ComSignUP(
                    companyId = email.value,
                    companyName = companyName.value,
                    email = email.value,
                    password = password.value,
                    managerName = managerName.value,
                    address = address.value
                )

                val response = SignUpCompRepo.signUpCompany(comSignUp)

                if (response != null && response.result == true) {
                    _signUpSuccess.value = true
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = response?.message ?: "Sign up failed"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Sign up failed: ${e.message}"
            }
        }
    }
}
