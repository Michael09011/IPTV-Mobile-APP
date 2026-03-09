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
    val epgUrl: String = "", // 플레이리스트별 EPG 주소 추가
    val channelCount: Int = 0,
    val lastUpdated: String = "방금 전",
    val type: PlaylistType = PlaylistType.URL,
    val displayOrder: Int = 0 // 플레이리스트 정렬 순서 추가
)

enum class PlaylistType {
    LOCAL, URL
}
