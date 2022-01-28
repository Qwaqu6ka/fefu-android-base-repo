package ru.fefu.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Activity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "active_type") val activeType: ActiveTypes,
    @ColumnInfo(name = "start_time") val startTime: Date,
    @ColumnInfo(name = "finish_time") val finishTime: Date
)
