package com.heungjun.popuplogintoken.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.repo.NetworkRepositoryUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserSignUpViewModel(
    private val repository: NetworkRepositoryUser = NetworkRepositoryUser()
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _detailAddress = MutableStateFlow("")
    val detailAddress: StateFlow<String> = _detailAddress

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _gender = MutableStateFlow("")
    val gender: StateFlow<String> = _gender

    private val _birth = MutableStateFlow("")
    val birth: StateFlow<String> = _birth

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    private val _postcode = MutableStateFlow("")
    val postcode: StateFlow<String> = _postcode

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address

    private val _nickname = MutableStateFlow("")
    val nickname: StateFlow<String> = _nickname

    private val _mapx = MutableStateFlow("")
    val mapx: StateFlow<String> = _mapx

    private val _mapy = MutableStateFlow("")
    val mapy: StateFlow<String> = _mapy

    private val _categories = MutableStateFlow(listOf<String>())
    val categories: StateFlow<List<String>> = _categories

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _signUpSuccess = MutableStateFlow(false)
    val signUpSuccess: StateFlow<Boolean> = _signUpSuccess

    // 각 필드에 대한 setter 함수들
    fun onEmailChange(newEmail: String) { _email.value = newEmail }
    fun onPasswordChange(newPassword: String) { _password.value = newPassword }
    fun onDetailAddressChange(newDetailAddress: String) { _detailAddress.value = newDetailAddress }
    fun onUsernameChange(newUsername: String) { _username.value = newUsername }
    fun onGenderChange(newGender: String) { _gender.value = newGender }
    fun onBirthChange(newBirth: String) { _birth.value = newBirth }
    fun onPhoneChange(newPhone: String) { _phone.value = newPhone }
    fun onPostcodeChange(newPostcode: String) { _postcode.value = newPostcode }
    fun onAddressChange(newAddress: String) { _address.value = newAddress }
    fun onNicknameChange(newNickname: String) { _nickname.value = newNickname }
    fun onMapxChange(newMapx: String) { _mapx.value = newMapx }
    fun onMapyChange(newMapy: String) { _mapy.value = newMapy }
    fun onCategoriesChange(newCategories: List<String>) { _categories.value = newCategories }

    fun signUp() {
        viewModelScope.launch {
            try {
                // 회원가입 API 호출 시 전송할 데이터
                val signUpData = mapOf(
                    "email" to _email.value,
                    "password" to _password.value,
                    "detailAddress" to _detailAddress.value,
                    "username" to _username.value,
                    "gender" to _gender.value,
                    "birth" to _birth.value,
                    "phone" to _phone.value,
                    "postcode" to _postcode.value,
                    "address" to _address.value,
                    "nickname" to _nickname.value,
                    "mapx" to _mapx.value,
                    "mapy" to _mapy.value,
                    "categories" to _categories.value.map { mapOf("category" to it) }
                )

                val response = repository.signUpApi(signUpData)
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