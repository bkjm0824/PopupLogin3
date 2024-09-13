package com.heungjun.popuplogintoken.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heungjun.popuplogintoken.R

@Composable
fun Header(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_popupstore),
            contentDescription = null,
            contentScale = ContentScale.Fit, //로고가 Box 크기에 맞게 조정
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp) // 높이를 적절히 조정하세요
        )
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview
@Composable
fun HeaderPreview() {
    Header()
}