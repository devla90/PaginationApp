package com.example.pagination.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pagination.domain.GetPhotosUseCase

class PhotosViewModelFactory(
    private val getPhotosUseCase: GetPhotosUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotosViewModel::class.java)){
            @Suppress("UNCHEKED_CAST")
            return PhotosViewModel(getPhotosUseCase) as T
        }
        throw IllegalStateException("Error View Model")
    }
}