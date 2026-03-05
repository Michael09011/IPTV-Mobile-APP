package com.example.minimaltv.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class Channel(
    @PrimaryKey
    val id: String,
    val playlistId: String, // 어떤 플레이리스트에 속해있는지 구분
    val name: String,
    val thumbnail: String? = null,
    val category: String,
    val currentProgram: String? = null,
    val isFavorite: Boolean = false,
    val streamUrl: String,
    val resolution: String = "HD"
)
