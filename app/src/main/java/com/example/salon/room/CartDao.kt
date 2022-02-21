package com.example.salon.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {

    @Query("SELECT * FROM cart")
    suspend fun getAll(): List<Cart>

    @Insert
    suspend fun insert(item: Cart)
}