package com.heungjun.popuplogintoken.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class ZipCodeViewModel : ViewModel() {
    val zipCode = mutableStateOf("")
    val address = mutableStateOf("")

    fun updateZipCode(newZipCode: String) {
        zipCode.value = newZipCode
    }

    fun updateAddress(newAddress: String) {
        address.value = newAddress
    }
}