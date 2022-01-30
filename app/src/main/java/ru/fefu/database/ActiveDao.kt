package ru.fefu.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.*

@Dao
interface ActiveDao {
    @Query("SELECT * FROM Activity WHERE finish_time IS NOT NULL")
    fun getAll(): LiveData<List<Activity>>

    @Query("SELECT * FROM Activity WHERE id=:id")
    fun getById(id: Long): Activity

    @Insert
    fun insert(active: Activity): Long

    @Query("UPDATE Activity SET finish_time = :time WHERE id = :id")
    fun finishActivity(id: Int, time: Date)

    @Query("SELECT id FROM Activity WHERE finish_time IS NULL ORDER BY id DESC LIMIT 1")
    fun getLastActiveId(): Int

    @Query("SELECT * FROM Activity ORDER BY id DESC LIMIT 1")
    fun getLastActive(): Activity

    @Query("SELECT * FROM Activity WHERE id=:id")
    fun getActivityByIdLive(id: Int): LiveData<Activity>

    @Query("SELECT * FROM Activity WHERE id=:id")
    fun getActivityById(id: Long): Activity

    @Query("UPDATE Activity SET coordinates = :coordinates WHERE id = :id")
    fun updateCoords(id: Long, coordinates: List<Point>)
}