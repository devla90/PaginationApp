package com.example.pagination.data


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pagination.domain.PhotosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PhotosRepositoryImpl(
    private val apiService: ApiService
) : PhotosRepository {


    override fun getPhotos(sol: Int, page: Int, api_key: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharacterPagingSource(apiService, sol, api_key) }
        ).flow
    }
}