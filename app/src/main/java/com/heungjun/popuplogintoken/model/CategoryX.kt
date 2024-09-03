package com.heungjun.popuplogintoken.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryX(
    @SerialName("category")
    val category: String
)