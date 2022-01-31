package ru.fefu.database

import androidx.room.*
import java.util.*

@Entity
data class Activity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "active_type") val activeType: ActiveTypes,
    @ColumnInfo(name = "start_time") val startTime: Date,
    @ColumnInfo(name = "finish_time") val finishTime: Date?,
    @ColumnInfo(name = "user_name") val userName: String,
    val coordinates: List<Point>
)
