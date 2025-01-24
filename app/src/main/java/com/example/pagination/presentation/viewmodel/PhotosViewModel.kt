package com.example.pagination.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagination.data.Photo
import com.example.pagination.data.Photos
import com.example.pagination.domain.GetPhotosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    data class Error(val message: String) : UiState()
}

class PhotosViewModel(
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _photosFlow = MutableStateFlow<Flow<PagingData<Photo>>?>(null)
    val photosFlow: StateFlow<Flow<PagingData<Photo>>?> get() = _photosFlow

    //https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=10&page=1&api_key=dUwg5MAJhyJZEQKO6BxXDUU4lXN4nFa6yac6lVd2
    init {
        getAllPhotos(10, 1, "dUwg5MAJhyJZEQKO6BxXDUU4lXN4nFa6yac6lVd2")
    }

    fun getAllPhotos(sol: Int, page: Int, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _uiState.value = UiState.Loading
                val pagingDataFlow = getPhotosUseCase(sol, page, apiKey)
                    .cachedIn(viewModelScope)
                _photosFlow.value = pagingDataFlow
                _uiState.value = UiState.Success
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.message}")
                Log.e("pagination", "Error: ${e.message}")
            }
        }
    }
}