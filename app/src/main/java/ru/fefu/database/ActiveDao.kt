package ru.fefu.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActiveDao {
    @Query("SELECT * FROM Activity")
    fun getAll(): LiveData<List<Activity>>

    @Query("SELECT * FROM Activity WHERE id=:id")
    fun getById(id: Int): Activity

    @Insert
    fun insert(active: Activity)
}