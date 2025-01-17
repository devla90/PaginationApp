package com.example.pagination.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("rovers/curiosity/photos")
    suspend fun getPhotos(@Query("sol") sol: Int, @Query("page") page: Int,@Query("api_key") api_key: String): Photos
}