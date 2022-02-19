package com.example.salon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salon.data.Employee
import com.example.salon.data.Service
import com.example.salon.repository.SalonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: SalonRepository): ViewModel() {

    var servicesLiveData = MutableLiveData<List<Service>>()

    var employeesLiveData = MutableLiveData<List<Employee>>()

    fun fetchServices() {
        viewModelScope.launch {
            val result = repository.getServices()
            if (result.isSuccessful)
                servicesLiveData.postValue(result.body()?.data)
        }
    }

    fun fetchEmployees() {
        viewModelScope.launch {
            val result = repository.getEmployees()
            if (result.isSuccessful)
                employeesLiveData.postValue(result.body()?.data)
        }
    }
}