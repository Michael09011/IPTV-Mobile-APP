package com.example.minimaltv.ui.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minimaltv.data.local.PlaylistDao
import com.example.minimaltv.data.model.Playlist
import com.example.minimaltv.data.parser.M3uParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URL

class PlaylistViewModel(private val playlistDao: PlaylistDao) : ViewModel() {
    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists = _playlists.asStateFlow()

    init {
        viewModelScope.launch {
            playlistDao.getAllPlaylists().collect {
                _playlists.value = it
            }
        }
    }

    fun addPlaylistFromUrl(name: String, url: String) {
        viewModelScope.launch {
            try {
                val content = URL(url).readText()
                val playlistId = java.util.UUID.randomUUID().toString()
                val channels = M3uParser.parse(content, playlistId)
                val newPlaylist = Playlist(
                    id = playlistId,
                    name = name,
                    url = url,
                    channelCount = channels.size
                )
                playlistDao.insertPlaylist(newPlaylist)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
