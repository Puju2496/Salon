package com.example.salon.repository

import com.example.salon.data.Data
import com.example.salon.data.Employee
import com.example.salon.data.Service
import com.example.salon.retrofit.ApiClient
import retrofit2.Response
import javax.inject.Inject

class SalonRepository @Inject constructor(private val  apiClient: ApiClient) {

    suspend fun getServices(): Response<Data<Service>> {
        return apiClient.salonApiService.getServices()
    }

    suspend fun getEmployees(): Response<Data<Employee>> {
        return apiClient.salonApiService.getEmployees()
    }
}