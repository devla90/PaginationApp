package com.example.pagination.domain

import com.example.pagination.data.Photos
import kotlinx.coroutines.flow.Flow

class GetPhotosUseCase(
    private val repository: PhotosRepository
) {
    operator fun invoke(sol: Int, page: Int, api_key: String): Flow<Photos> {
        return repository.getPhotos(sol, page, api_key)
    }
}