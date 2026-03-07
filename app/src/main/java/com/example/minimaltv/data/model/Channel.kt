package com.example.minimaltv.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class Channel(
    @PrimaryKey
    val id: String,
    val playlistId: String,
    val name: String,
    val thumbnail: String? = null,
    val category: String,
    val currentProgram: String? = null,
    val isFavorite: Boolean = false,
    val streamUrl: String,
    val resolution: String = "HD",
    val lastWatched: Long = 0 // 최근 시청 시간 (0이면 시청 기록 없음)
)
