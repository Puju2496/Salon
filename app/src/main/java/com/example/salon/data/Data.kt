package com.example.salon.data

data class Data<T>(
    val responseCode: Int,
    val data: List<T>
)
