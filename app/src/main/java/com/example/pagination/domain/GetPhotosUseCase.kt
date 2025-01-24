package com.example.pagination.domain

import androidx.paging.PagingData
import com.example.pagination.data.Photo
import com.example.pagination.data.Photos
import kotlinx.coroutines.flow.Flow

class GetPhotosUseCase(
    private val repository: PhotosRepository
) {
    operator fun invoke(sol: Int, page: Int, api_key: String): Flow<PagingData<Photo>> {
        return repository.getPhotos(sol, page, api_key)
    }
}