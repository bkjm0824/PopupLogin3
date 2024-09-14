package com.heungjun.popuplogintoken.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse3<T>(
    @SerialName("result") val result: Boolean, // 요청 성공 여부
    @SerialName("message") val message: String, // 서버 메시지
    @SerialName("data") val data: T? = null // 응답 데이터 (nullable)
)


//사용자 사전예약 데이터 모델
@Serializable
data class UserReservation(
    @SerialName("id")
    val id:Int,
    @SerialName("day")
    val day: String,  //예약할 요일
    @SerialName("reservation_time")
    val reservationTime: String,   //예약 시간
    @SerialName("reservation_count")
    val reservationCount: Int, //예약 인원
)