package com.heungjun.popuplogintoken.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class ApiResponse( // 서버로부터의 응답 데이터를 담는 클래스
    @SerialName("data") // JSON 키와 데이터 클래스의 변수명을 매핑
    val `data`: Data, // 실제 데이터 부분을 담는 객체
    @SerialName("message")
    val message: String, // 응답 메시지 (성공 또는 오류 메시지 등)
    @SerialName("result")
    val result: Boolean, // 성공 여부를 나타내는 플래그
)

@kotlinx.serialization.Serializable
data class Login( // 로그인 요청 시 서버로 전송할 데이터 클래스
    @SerialName("email")
    val email: String, // 사용자의 이메일 주소
    @SerialName("password")
    val password: String // 사용자의 비밀번호
)

@Serializable
data class Data( // 응답의 `data` 필드를 담는 데이터 클래스
    @SerialName("exprTime")
    val exprTime: Int, // 토큰의 만료 시간 (초 단위)
    @SerialName("token")
    val token: String // 인증 토큰
)