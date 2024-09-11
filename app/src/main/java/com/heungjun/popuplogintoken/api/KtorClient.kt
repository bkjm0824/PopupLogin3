package com.heungjun.popuplogintoken.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Ktor HTTP 클라이언트를 구성하는 객체
object KtorClient {

    // JSON 파서를 설정하는 부분, 기본값을 설정하고 알 수 없는 키를 무시하게 설정
    private val json = Json {
        encodeDefaults = true  // 기본값을 JSON에 포함시킴
        ignoreUnknownKeys = true  // JSON에 없는 속성은 무시
    }

    // Ktor HTTP 클라이언트 설정
    val httpClient = HttpClient {
        // ContentNegotiation 플러그인 설치, JSON 변환기를 사용해 응답 데이터를 파싱
        install(ContentNegotiation) {
            json(json)  // 앞서 정의한 JSON 설정을 사용하여 ContentNegotiation 설치
        }

        // Logging 플러그인 설치, 로그를 통해 모든 요청 및 응답의 정보를 볼 수 있음
        install(Logging) {
            logger = object : Logger {  // 로그를 기록하는 로거 정의
                override fun log(message: String) {
                    Log.d("KtorClient", "API log: $message")  // 로그를 디버그 출력으로 남김
                }
            }
            level = LogLevel.ALL  // 모든 로그 레벨을 기록 (HEADER, BODY 등)
        }

        // HTTP 요청 타임아웃 설정
        install(HttpTimeout) {
            requestTimeoutMillis = 10000  // 요청 타임아웃을 10초로 설정
            connectTimeoutMillis = 10000  // 연결 타임아웃을 10초로 설정
            socketTimeoutMillis = 10000  // 소켓 타임아웃을 10초로 설정
        }

        // 기본적인 API 요청 시 적용되는 설정
        defaultRequest {
            contentType(ContentType.Application.Json)  // 요청의 콘텐츠 타입을 JSON으로 설정
            accept(ContentType.Application.Json)  // 응답의 콘텐츠 타입으로 JSON을 기대
        }
    }
}