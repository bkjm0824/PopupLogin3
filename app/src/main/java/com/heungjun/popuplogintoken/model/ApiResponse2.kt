package com.heungjun.popuplogintoken.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// detail 조회
@Serializable
data class ApiResponse2(
    @SerialName("result")
    val result: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val popupStores: List<PopupStore>
)

@Serializable
data class PopupStore(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("address")
    val address: String? = null,
    @SerialName("postCode")
    val postCode: String? = null,
    @SerialName("detailAddress")
    val detailAddress: String? = null,
    @SerialName("startDate")
    val startDate: String? = null,
    @SerialName("endDate")
    val endDate: String? = null,
    @SerialName("telephone")
    val telephone: String? = null,
    @SerialName("subway")
    val subway: String? = null,
    @SerialName("mapx")
    val mapx: String? = null,
    @SerialName("mapy")
    val mapy: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("link")
    val link: String? = null,
    @SerialName("categories")
    val categories: List<CategoryDetail>,
    @SerialName("popupImages")
    val popupImages: List<PopupImage>? // Modified to be a list of PopupImage objects
)

@Serializable
data class PopupImage(
    @SerialName("id")
    val id: Int,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("popupStoreId")
    val popupStoreId: Int? = null
)

@Serializable
data class CategoryDetail(
    @SerialName("categoryId")
    val categoryId: Int,
    @SerialName("category")
    val category: String
)