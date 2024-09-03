package com.heungjun.popuplogintoken.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heungjun.popuplogintoken.api.CategoryRepo
import com.heungjun.popuplogintoken.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CategoryVM : ViewModel() {

    // 카테고리 목록을 보관하는 StateFlow, UI에서 이 데이터를 구독하여 상태 변경을 반영
    val categoriesFlow = MutableStateFlow<List<Category>?>(null)  // null 초기화로 로딩 상태 표현

    init {
        Log.d("CategoryVM", "CategoryVM init called")

        // 뷰모델이 초기화될 때 카테고리 목록을 비동기적으로 가져오는 작업을 수행
        viewModelScope.launch {
            kotlin.runCatching {
                // CategoryRepo의 fetchCategories 함수를 호출하여 카테고리 목록을 가져옴
                CategoryRepo.fetchCategories()
            }.onSuccess { fetchedCategories ->
                Log.d("CategoryVM", "onSuccess: Fetched categories")
                categoriesFlow.value = fetchedCategories ?: listOf()  // null이면 빈 리스트로 설정
            }.onFailure {
                // 데이터 가져오기에 실패한 경우 로그에 실패 메시지 출력
                Log.d("CategoryVM", "onFailure: Failed to fetch categories", it)
                categoriesFlow.value = listOf()  // 실패 시 빈 리스트로 설정
            }
        }
    }
}