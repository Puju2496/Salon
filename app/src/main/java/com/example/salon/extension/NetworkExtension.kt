package com.example.salon.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

val Context.isConnectedToNetwork: Boolean?
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        return connectivityManager.getNetworkCapabilities(activeNetwork)?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }