package com.heungjun.popuplogintoken.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.heungjun.popuplogintoken.api.NetworkRepository
import com.heungjun.popuplogintoken.model.PopupStore
import com.heungjun.popuplogintoken.viewmodel.SearchViewModel
import com.heungjun.popuplogintoken.viewmodel.SearchViewModelFactory

@Composable
fun SearchScreen(onStoreClicked: (PopupStore) -> Unit, repository: NetworkRepository) {
    val searchQuery = remember { mutableStateOf("") }
    val viewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(repository)
    )
    val uiState = viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search Bar
        SearchBar(
            query = searchQuery.value,
            onQueryChanged = { searchQuery.value = it },
            onSearchClicked = {
                viewModel.search(searchQuery.value)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        when {
            uiState.value.isLoading -> {
                Text(text = "Loading...")
            }
            uiState.value.error != null -> {
                Text(text = "Error: ${uiState.value.error}")
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.value.popupStores) { store ->
                        // Pass the index to SearchResultItem
                        val index = uiState.value.popupStores.indexOf(store)
                        SearchResultItem(store, index, modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                        ) {
                            onStoreClicked(store)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit, onSearchClicked: () -> Unit) {
    val textState = remember { mutableStateOf(query) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
                onQueryChanged(it)
            },
            label = { Text("검색") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(onClick = onSearchClicked) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        }
    }
}

@Composable
fun SearchResultItem(store: PopupStore, index: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    // Define the list of image URLs to be used
    val imageUrls = listOf(
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_VKXdGNcIYX1P_y7gHycdiJOnFhb_1vpokg&s",
        "https://news.nateimg.co.kr/orgImg/se/2024/03/28/2D6T7LN09P_1.jpg",
        "https://cdn.dailygrid.net/news/photo/201908/267517_166557_4755.jpg",
        "https://newsroom.smilegate.com/s3/board/220330_00.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQoSbqiTeB9z2Kb2MSbxiVmLZZHPCqWdQuSw&s",
        "https://images.munto.kr/production-socialing/1716729560828-photo-aiixc-384151-0",
        "https://cdn.econovill.com/news/photo/202401/641224_585187_359.jpg",
        "https://mblogthumb-phinf.pstatic.net/MjAyMzAzMTZfMTQ1/MDAxNjc4OTMxNTI4ODU3.tOpsEXnN7Byr6aQTMUNrH-5x2fizSxzT_JJSbjcgbGgg.ag4EINpirLxT8gXVv0u5SshzDGi9nFbT5qn_oruF1wwg.PNG.toun28/image.png?type=w800"
    )

    // Determine the image URL based on the index
    val imageUrl = imageUrls.getOrElse(index % imageUrls.size) { "" }

    // Convert categories list to a comma-separated string
    val categoriesText = store.categories.joinToString(", ") { it.category }

    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image on the left
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp) // Fixed size for the image
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Text content on the right
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = store.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = categoriesText, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

