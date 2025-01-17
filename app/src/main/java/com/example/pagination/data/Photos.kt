package com.example.pagination.data

data class Photos(
    val photos: List<Photo>
)

data class Photo(
    val id: Int,
    val img_src: String
)