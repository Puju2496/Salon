package com.example.salon.retrofit

import com.example.salon.data.Data
import com.example.salon.data.Employee
import com.example.salon.data.Service
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface SalonApiService {

    @GET("/challenge-services/")
    suspend fun getServices(): Response<Data<Service>>

    @Headers("Cache-Control: no-cache")
    @GET("/challenge-employees/")
    suspend fun getEmployees(): Response<Data<Employee>>
}