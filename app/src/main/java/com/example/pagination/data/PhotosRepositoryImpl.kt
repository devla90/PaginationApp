package com.example.pagination.data

import com.example.pagination.domain.PhotosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PhotosRepositoryImpl(
    private val apiService: ApiService
): PhotosRepository {

    override fun getPhotos(sol: Int, page: Int, api_key: String): Flow<Photos> = flow {
        emit(apiService.getPhotos(sol, page, api_key))
    }

}