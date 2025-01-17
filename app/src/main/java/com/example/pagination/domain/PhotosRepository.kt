package com.example.pagination.domain

import com.example.pagination.data.Photos
import kotlinx.coroutines.flow.Flow

interface PhotosRepository {
    fun getPhotos(sol: Int, page: Int, api_key: String): Flow<Photos>
}