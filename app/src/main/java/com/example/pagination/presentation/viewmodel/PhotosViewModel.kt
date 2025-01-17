package com.example.pagination.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pagination.data.Photo
import com.example.pagination.data.Photos
import com.example.pagination.domain.GetPhotosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

sealed class  UiState {
    object Idle: UiState()
    data class Success(val data: List<Photo>): UiState()
}
class PhotosViewModel(
    private val getPhotosUseCase: GetPhotosUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState
    init {
        getAllPhotos(10, 1, "dUwg5MAJhyJZEQKO6BxXDUU4lXN4nFa6yac6lVd2")
    }

    fun getAllPhotos(sol: Int, page: Int, api_key: String){
        viewModelScope.launch {
            try {
                getPhotosUseCase.invoke(sol,page,api_key)
                    .flowOn(Dispatchers.IO)
                    .collect{ response ->
                        _uiState.value = UiState.Success(response.photos)
                        Log.i("console_photso", response.toString())
                    }
            }catch (ex: Exception){
                Log.e("console_photso", ex.toString())
            }
        }

    }
}