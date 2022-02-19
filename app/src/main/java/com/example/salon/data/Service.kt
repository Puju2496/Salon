package com.example.salon.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Service(
    val id: Int = 0,
    val name: String? = null,
    val price: Int = 0,
    val image: String? = null
): Parcelable

data class Employee(
    val id: Int = 0,
    val name: String? = null,
    val image: String? = null
)
