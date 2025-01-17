package com.example.pagination.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pagination.PaginationApp
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

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Prueba")
        val photosList = (uiState as UiState.Success).data
        LazyColumn {
            items(photosList) { photo ->
                Text(photo.img_src)
            }
        }
    }
}