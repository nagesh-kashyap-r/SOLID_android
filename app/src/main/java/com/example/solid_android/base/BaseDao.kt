package com.example.solid_android.base


import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T?): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entityList: List<T>?): List<Long>

    @Update
    fun update(entity: T)

    @Delete
    fun delete(entity: T)
}