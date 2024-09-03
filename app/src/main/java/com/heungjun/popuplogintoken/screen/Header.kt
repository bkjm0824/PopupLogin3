package com.heungjun.popuplogintoken.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heungjun.popuplogintoken.R

@Composable
fun Header(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_popupstore),
        contentDescription = "Popup Store",
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp) // 높이를 적절히 조정하세요
    )
    Spacer(modifier = Modifier.height(10.dp))
//    HorizontalDivider(
//        thickness = 4.dp,
//        color = Color.Black // 수평 구분선을 검은색으로 설정
//    )
}

@Preview
@Composable
fun HeaderPreview() {
    Header()
}