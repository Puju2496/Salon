package com.example.salon.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import timber.log.Timber
import java.net.InetSocketAddress
import java.net.Socket

val Context.isConnectedToNetwork: Boolean
    get() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        return connectivityManager.getNetworkCapabilities(activeNetwork).run {
            this != null && (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            )) && hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && hasCapability(
                NetworkCapabilities.NET_CAPABILITY_VALIDATED
            )
        }
    }

val isOnline: Boolean
    get() {
        return try {
            val timeOut = 3000
            val socket = Socket()
            val address = InetSocketAddress("8.8.8.8", 53)
            socket.connect(address, timeOut)
            socket.close()
            true
        } catch (e: Exception) {
            Timber.e("Network Connection: ${e.message}")
            false
        }
    }