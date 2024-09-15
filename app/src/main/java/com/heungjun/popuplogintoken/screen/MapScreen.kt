package com.heungjun.popuplogintoken.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.NaverMap
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val mapView = rememberMapViewWithLifecycle(context)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun rememberMapViewWithLifecycle(context: Context): MapView {
    val mapView = MapView(context)

    // Initialize Naver Map SDK
    NaverMapSdk.getInstance(context).client =
        NaverMapSdk.NaverCloudPlatformClient("yiu50g266x")

    mapView.getMapAsync { naverMap ->
        setupNaverMap(naverMap)
    }

    return mapView
}

fun setupNaverMap(naverMap: NaverMap) {
    // 서울의 좌표 (위도: 37.5665, 경도: 126.9780)로 설정
    val seoulLatLng = LatLng(37.5665, 126.9780)

    // NaverMap의 초기 설정
    with(naverMap) {
        moveCamera(CameraUpdate.scrollTo(seoulLatLng))

        // UI 설정
        uiSettings.isLocationButtonEnabled = true
        uiSettings.isCompassEnabled = true
        uiSettings.isZoomControlEnabled = true
    }
}


