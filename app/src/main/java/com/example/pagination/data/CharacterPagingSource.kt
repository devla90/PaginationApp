package com.example.pagination.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import okio.IOException

class CharacterPagingSource(
    private val apiService: ApiService,
    private val sol: Int,
    private val apiKey: String
): PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getPhotos(sol, currentPage, apiKey)
            val photos = response.photos
            Log.i("photosImpl",photos.toString())

            LoadResult.Page(
                data = photos,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (photos.isEmpty()) null else currentPage + 1
            )
        }catch (e:IOException){
            LoadResult.Error(e)
        }
    }

}