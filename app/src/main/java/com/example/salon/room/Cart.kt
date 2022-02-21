package com.example.salon.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var employees: String,
    var services: String
)
