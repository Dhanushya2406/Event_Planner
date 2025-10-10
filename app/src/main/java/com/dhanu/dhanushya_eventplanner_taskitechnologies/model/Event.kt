package com.dhanu.dhanushya_eventplanner_taskitechnologies.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("events")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val date: Long,
    val time: String,
)
