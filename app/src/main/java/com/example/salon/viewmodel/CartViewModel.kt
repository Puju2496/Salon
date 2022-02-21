package com.example.salon.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.salon.data.Cart
import com.example.salon.data.Employee
import com.example.salon.data.Service
import com.example.salon.room.CartDao
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val dao: CartDao): ViewModel() {

    private val gson = GsonBuilder().setLenient().create()
    var cartListLiveData = MutableLiveData<List<Cart>>()

    fun getCartList() {
        viewModelScope.launch {
            val cart = dao.getAll()
            val mappedCartList = cart.map {
                com.example.salon.data.Cart(
                    employees = getMappedEmployees(it.employees),
                    services = getMappedServices(it.services)
                )
            }
            mapCartList(mappedCartList)
        }
    }

    private fun mapCartList(cartList: List<com.example.salon.data.Cart>) {
        val mappedList = mutableListOf<com.example.salon.data.Cart>()
        cartList.groupBy {
            it.employees
        }.map {
            val services = arrayListOf<Service>()
            it.value.map { cart -> services.addAll(cart.services) }
            mappedList.add(com.example.salon.data.Cart(employees = it.key, services = services))
        }
        cartListLiveData.postValue(mappedList)
    }

    private fun getMappedEmployees(employees: String): ArrayList<Employee> = gson.fromJson(employees, object : TypeToken<ArrayList<Employee>>() {}.type)

    private fun getMappedServices(services: String): ArrayList<Service> = gson.fromJson(services, object : TypeToken<ArrayList<Service>>() {}.type)
}