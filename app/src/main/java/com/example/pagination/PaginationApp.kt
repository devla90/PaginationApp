package com.example.pagination

import android.app.Application
import com.example.pagination.data.ApiService
import com.example.pagination.data.PhotosRepositoryImpl
import com.example.pagination.domain.PhotosRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaginationApp: Application(){
    lateinit var photosApiService: ApiService
    lateinit var photosRepository: PhotosRepository

    override fun onCreate() {
        super.onCreate()
        photosApiService = Retrofit.Builder().baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
            .create(ApiService::class.java)

        photosRepository = PhotosRepositoryImpl(photosApiService)
    }

}