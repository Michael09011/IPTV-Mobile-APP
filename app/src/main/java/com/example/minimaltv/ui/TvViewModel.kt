package com.example.minimaltv.ui

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.minimaltv.data.local.AppDatabase
import com.example.minimaltv.data.local.SettingsManager
import com.example.minimaltv.data.model.Playlist
import com.example.minimaltv.data.model.Channel
import com.example.minimaltv.data.parser.M3uParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.UUID

class TvViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val playlistDao = database.playlistDao()
    private val channelDao = database.channelDao()
    val settingsManager = SettingsManager(application)

    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> = _playlists.asStateFlow()

    private val _favorites = MutableStateFlow<List<Channel>>(emptyList())
    val favorites: StateFlow<List<Channel>> = _favorites.asStateFlow()

    private val _recentChannels = MutableStateFlow<List<Channel>>(emptyList())
    val recentChannels: StateFlow<List<Channel>> = _recentChannels.asStateFlow()

    private val _selectedChannels = MutableStateFlow<List<Channel>>(emptyList())
    val selectedChannels: StateFlow<List<Channel>> = _selectedChannels.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _currentPlaylistId = MutableStateFlow<String?>(null)
    val currentPlaylistId: StateFlow<String?> = _currentPlaylistId.asStateFlow()

    private val _currentPlayingChannel = MutableStateFlow<Channel?>(null)
    val currentPlayingChannel: StateFlow<Channel?> = _currentPlayingChannel.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                playlistDao.getAllPlaylists()
                    .catch { e -> e.printStackTrace() }
                    .collect { _playlists.value = it }
            } catch (e: Exception) { e.printStackTrace() }
        }
        viewModelScope.launch {
            try {
                channelDao.getFavoriteChannels()
                    .catch { e -> e.printStackTrace() }
                    .collect { _favorites.value = it }
            } catch (e: Exception) { e.printStackTrace() }
        }
        viewModelScope.launch {
            try {
                channelDao.getRecentChannels()
                    .catch { e -> e.printStackTrace() }
                    .collect { _recentChannels.value = it }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun loadChannelsForPlaylist(playlistId: String) {
        _currentPlaylistId.value = playlistId
        viewModelScope.launch {
            channelDao.getChannelsByPlaylist(playlistId)
                .catch { e -> e.printStackTrace() }
                .collect { _selectedChannels.value = it }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectChannel(channel: Channel) {
        _currentPlayingChannel.value = channel
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedChannel = channel.copy(lastWatched = System.currentTimeMillis())
                channelDao.updateChannel(updatedChannel)
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun nextChannel() {
        val current = _currentPlayingChannel.value ?: return
        val list = _selectedChannels.value
        if (list.isNotEmpty()) {
            val index = list.indexOfFirst { it.id == current.id }
            if (index != -1 && index < list.size - 1) {
                selectChannel(list[index + 1])
            }
        }
    }

    fun prevChannel() {
        val current = _currentPlayingChannel.value ?: return
        val list = _selectedChannels.value
        if (list.isNotEmpty()) {
            val index = list.indexOfFirst { it.id == current.id }
            if (index > 0) {
                selectChannel(list[index - 1])
            }
        }
    }

    fun addPlaylistFromUrl(name: String, url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val playlistId = UUID.randomUUID().toString()
                val content = URL(url).readText()
                val channels = M3uParser.parse(content, playlistId)
                val playlist = Playlist(id = playlistId, name = name, url = url, channelCount = channels.size)
                playlistDao.insertPlaylist(playlist)
                channelDao.insertChannels(channels)
                showToast("플레이리스트 추가 완료")
            } catch (e: Exception) {
                showToast("추가 실패")
            }
        }
    }

    fun addPlaylistFromLocalFile(name: String, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val playlistId = UUID.randomUUID().toString()
                val content = getApplication<Application>().contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }
                if (content != null) {
                    val channels = M3uParser.parse(content, playlistId)
                    val playlist = Playlist(id = playlistId, name = name, url = uri.toString(), channelCount = channels.size)
                    playlistDao.insertPlaylist(playlist)
                    channelDao.insertChannels(channels)
                }
            } catch (e: Exception) {
                showToast("파일 추가 실패")
            }
        }
    }

    fun toggleFavorite(channel: Channel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedChannel = channel.copy(isFavorite = !channel.isFavorite)
                channelDao.updateChannel(updatedChannel)
                if (_currentPlayingChannel.value?.id == channel.id) {
                    _currentPlayingChannel.value = updatedChannel
                }
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                playlistDao.deletePlaylistById(playlist.id)
                channelDao.deleteChannelsByPlaylist(playlist.id)
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun refreshPlaylist(playlist: Playlist) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val content = URL(playlist.url).readText()
                val channels = M3uParser.parse(content, playlist.id)
                channelDao.deleteChannelsByPlaylist(playlist.id)
                channelDao.insertChannels(channels)
                playlistDao.insertPlaylist(playlist.copy(channelCount = channels.size))
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun refreshAllPlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            _playlists.value.forEach { refreshPlaylist(it) }
        }
    }

    fun renamePlaylist(playlist: Playlist, newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                playlistDao.insertPlaylist(playlist.copy(name = newName))
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    private suspend fun showToast(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
        }
    }
}
