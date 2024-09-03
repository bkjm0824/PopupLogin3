package com.heungjun.popuplogintoken.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.heungjun.popuplogintoken.viewmodel.CategoryVM

@Composable
fun CategorySelectionScreen(categoryVM: CategoryVM = viewModel()) {
    var selectedCategories by remember { mutableStateOf(listOf<String>()) }
    val categories by categoryVM.categoriesFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "선호 카테고리 (최대 3개 선택)",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when {
            categories == null -> {
                CircularProgressIndicator()
            }
            categories!!.isEmpty() -> {
                Text(
                    text = "카테고리를 불러오지 못했습니다. 네트워크를 확인해주세요.",
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
            else -> {
                CategorySelection(
                    categories = categories!!.map { it.category }, // 카테고리 이름 리스트로 변환
                    selectedCategories = selectedCategories,
                    onSelectionChange = { selectedCategories = it }
                )
            }
        }
    }
}

@Composable
fun CategorySelection(
    categories: List<String>,
    selectedCategories: List<String>,
    onSelectionChange: (List<String>) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // 3개의 카테고리를 한 줄로 배치
        categories.chunked(3).forEach { rowCategories ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                rowCategories.forEach { category ->
                    val isSelected = selectedCategories.contains(category)
                    val displayText = if (category.length > 6) category.substring(0, 6) else category
                    Button(
                        onClick = {
                            val newSelection = selectedCategories.toMutableList()
                            if (isSelected) {
                                newSelection.remove(category)
                            } else if (newSelection.size < 3) {
                                newSelection.add(category)
                            }
                            onSelectionChange(newSelection)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Color.Black else Color.White
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)  // 각 버튼을 동일한 너비로 설정
                            .height(50.dp)  // 버튼 높이를 동일하게 설정
                    ) {
                        Text(
                            text = displayText,
                            color = if (isSelected) Color.White else Color.Black,
                            fontSize = 10.4.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }
                // 만약 행의 카테고리 수가 3개보다 적다면 빈 공간을 추가
                if (rowCategories.size < 3) {
                    repeat(3 - rowCategories.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

// 프리뷰를 위한 가상 데이터
@Preview(showBackground = true)
@Composable
fun CategorySelectionScreenPreview() {
    // 가상 데이터 설정
    val dummyCategories = listOf(
        "화장품", "캐릭터", "도서/음반",
        "패션", "인테리어", "전자제품",
        "향수", "음식", "음료",
        "주류", "장난감", "문구",
        "가정", "생활용품", "기타행사"
    )

    // 미리보기에서 ViewModel을 사용할 수 없으므로 가상 데이터를 사용하여 미리보기 화면을 렌더링
    CategorySelection(
        categories = dummyCategories,
        selectedCategories = listOf("화장품", "캐릭터"),
        onSelectionChange = {}
    )
}