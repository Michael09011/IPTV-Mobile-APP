package com.example.minimaltv.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "playlists")
data class Playlist(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val url: String,
    val channelCount: Int = 0,
    val lastUpdated: String = "방금 전",
    val type: PlaylistType = PlaylistType.URL
)

enum class PlaylistType {
    LOCAL, URL
}
