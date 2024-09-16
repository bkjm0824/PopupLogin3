package com.heungjun.popuplogintoken.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReservationList(
    @SerialName("data")
    val `data`: List<UserReservationList>,
    @SerialName("message")
    val message: String,
    @SerialName("result")
    val result: Boolean
)


@Serializable
data class UserReservationList(
    @SerialName("date")
    val date: String,
    @SerialName("Id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("numberOfPeople")
    val numberOfPeople: Int,
    @SerialName("startTime")
    val startTime: String,
    @SerialName("title")
    val title: String
)