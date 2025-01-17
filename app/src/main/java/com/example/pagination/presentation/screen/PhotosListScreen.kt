package com.example.pagination.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.pagination.PaginationApp
import com.example.pagination.data.Photo
import com.example.pagination.domain.GetPhotosUseCase
import com.example.pagination.presentation.viewmodel.PhotosViewModel
import com.example.pagination.presentation.viewmodel.PhotosViewModelFactory
import com.example.pagination.presentation.viewmodel.UiState

@Composable
fun PhotosListScreen() {

    val context = LocalContext.current
    val application = context.applicationContext as PaginationApp
    val photosRepository = application.photosRepository
    val getPhotosUseCase = GetPhotosUseCase(photosRepository)
    val viewModelFactory = PhotosViewModelFactory(getPhotosUseCase)
    val viewModel: PhotosViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.uiState.collectAsState()
    val photosFlow = viewModel.photosFlow.collectAsState()

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        when (uiState) {
            is UiState.Success -> {
                photosFlow.value?.let { pagingDataFlow ->
                    val photos = pagingDataFlow.collectAsLazyPagingItems()
                    PhotosList(photos)
                }
            }
            is UiState.Error -> {
                Text(
                    text = (uiState as UiState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.fillMaxSize()
                )
            }
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun PhotosList(pagingData: LazyPagingItems<Photo>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pagingData.itemCount) {
            pagingData[it]?.let { characterModel ->
                ItemList(characterModel)
            }
        }

        pagingData.apply {
            when {
                loadState.append is androidx.paging.LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                }

                loadState.append is androidx.paging.LoadState.Error -> {
                    item {
                        Text(
                            "Error al cargar más elementos",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemList(photo: Photo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = photo.img_src,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Captured Id: ${photo.id}", style = MaterialTheme.typography.titleMedium)
        }
    }
}