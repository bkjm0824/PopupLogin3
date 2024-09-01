package com.heungjun.popuplogintoken.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.repo.NetworkRepositoryCompany
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CompanySignUpViewModel(
    private val repository: NetworkRepositoryCompany = NetworkRepositoryCompany()
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _companyName = MutableStateFlow("")
    val companyName: StateFlow<String> = _companyName

    private val _companyId = MutableStateFlow("")
    val companyId: StateFlow<String> = _companyId

    private val _managerName = MutableStateFlow("")
    val managerName: StateFlow<String> = _managerName

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address

    private val _detailAddress = MutableStateFlow("")
    val detailAddress: StateFlow<String> = _detailAddress

    private val _postCode = MutableStateFlow("")
    val postCode: StateFlow<String> = _postCode

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess: StateFlow<Boolean> = _signUpSuccess

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onCompanyNameChange(newCompanyName: String) {
        _companyName.value = newCompanyName
    }

    fun onCompanyIdChange(newCompanyId: String) {
        _companyId.value = newCompanyId
    }

    fun onManagerNameChange(newManagerName: String) {
        _managerName.value = newManagerName
    }

    fun onAddressChange(newAddress: String) {
        _address.value = newAddress
    }

    fun onDetailAddressChange(newDetailAddress: String) {
        _detailAddress.value = newDetailAddress
    }

    fun onPostCodeChange(newPostCode: String) {
        _postCode.value = newPostCode
    }

    fun signUp() {
        viewModelScope.launch {
            try {
                val response = repository.signUpApi(
                    email = _email.value,
                    password = _password.value,
                    companyName = _companyName.value,
                    companyId = _companyId.value,
                    managerName = _managerName.value,
                    address = _address.value,
                    detailAddress = _detailAddress.value,
                    postCode = _postCode.value
                )
                if (response.result) {
                    _signUpSuccess.value = true
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = response.message
                }
            } catch (e: Exception) {
                _errorMessage.value = "Sign up failed: ${e.message}"
            }
        }
    }
}