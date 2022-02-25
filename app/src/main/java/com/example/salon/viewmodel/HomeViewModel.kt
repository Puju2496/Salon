package com.example.salon.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salon.R
import com.example.salon.data.Employee
import com.example.salon.data.Service
import com.example.salon.extension.isConnectedToNetwork
import com.example.salon.extension.isOnline
import com.example.salon.repository.SalonRepository
import com.example.salon.room.Cart
import com.example.salon.room.CartDao
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SalonRepository,
    private val dao: CartDao
) : ViewModel() {

    private val gson = GsonBuilder().create()

    var servicesLiveData = MutableLiveData<List<Service>>()
    var employeesLiveData = MutableLiveData<List<Employee>>()

    var servicesErrorLiveData = MutableLiveData<String>()
    var employeesErrorLiveData = MutableLiveData<String>()

    fun fetchServices(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (context.isConnectedToNetwork) {
                if (isOnline) {
                    val result = repository.getServices()
                    if (result.isSuccessful) {
                        servicesLiveData.postValue(result.body()?.data)
                        servicesErrorLiveData.postValue("")
                    } else
                        servicesErrorLiveData.postValue(result.message())
                } else
                    servicesErrorLiveData.postValue(context.getString(R.string.invalid_network_error))

            } else
                servicesErrorLiveData.postValue(context.getString(R.string.connect_error))
        }
    }

    fun fetchEmployees(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (context.isConnectedToNetwork) {
                if (isOnline) {
                    val result = repository.getEmployees()
                    if (result.isSuccessful) {
                        employeesLiveData.postValue(result.body()?.data)
                        employeesErrorLiveData.postValue("")
                    } else
                        employeesErrorLiveData.postValue(result.message())
                } else
                    servicesErrorLiveData.postValue(context.getString(R.string.invalid_network_error))

            } else
                servicesErrorLiveData.postValue(context.getString(R.string.connect_error))
        }
    }

    fun addToCart(context: Context, employees: ArrayList<Employee>?, services: ArrayList<Service>) {
        viewModelScope.launch(Dispatchers.IO) {
            val cart = Cart(employees = gson.toJson(employees), services = gson.toJson(services))
            dao.insert(cart).apply {
                Toast.makeText(
                    context,
                    context.getString(R.string.items_Added_notify),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}