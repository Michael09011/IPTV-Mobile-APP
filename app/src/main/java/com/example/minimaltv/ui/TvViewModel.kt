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

    private val _selectedChannels = MutableStateFlow<List<Channel>>(emptyList())
    val selectedChannels: StateFlow<List<Channel>> = _selectedChannels.asStateFlow()

    private val _currentPlayingChannel = MutableStateFlow<Channel?>(null)
    val currentPlayingChannel: StateFlow<Channel?> = _currentPlayingChannel.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            playlistDao.getAllPlaylists().collect { _playlists.value = it }
        }
        viewModelScope.launch {
            channelDao.getFavoriteChannels().collect { _favorites.value = it }
        }
    }

    fun loadChannelsForPlaylist(playlistId: String) {
        viewModelScope.launch {
            channelDao.getChannelsByPlaylist(playlistId).collect {
                _selectedChannels.value = it
            }
        }
    }

    fun selectChannel(channel: Channel) {
        _currentPlayingChannel.value = channel
    }

    fun nextChannel() {
        val current = _currentPlayingChannel.value ?: return
        val list = _selectedChannels.value.ifEmpty { _favorites.value }
        val index = list.indexOfFirst { it.id == current.id }
        if (index != -1 && index < list.size - 1) {
            _currentPlayingChannel.value = list[index + 1]
        }
    }

    fun prevChannel() {
        val current = _currentPlayingChannel.value ?: return
        val list = _selectedChannels.value.ifEmpty { _favorites.value }
        val index = list.indexOfFirst { it.id == current.id }
        if (index > 0) {
            _currentPlayingChannel.value = list[index - 1]
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
                showToast("플레이리스트 추가 완료: ${channels.size}개 채널")
            } catch (e: Exception) {
                showToast("추가 실패: URL을 확인해주세요.")
                e.printStackTrace()
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
                    showToast("파일 추가 완료: ${channels.size}개 채널")
                }
            } catch (e: Exception) {
                showToast("파일 불러오기 실패")
                e.printStackTrace()
            }
        }
    }

    fun renamePlaylist(playlist: Playlist, newName: String) {
        viewModelScope.launch {
            playlistDao.insertPlaylist(playlist.copy(name = newName))
        }
    }

    fun toggleFavorite(channel: Channel) {
        viewModelScope.launch {
            val updatedChannel = channel.copy(isFavorite = !channel.isFavorite)
            channelDao.updateChannel(updatedChannel)
        }
    }

    fun deletePlaylist(playlist: Playlist) {
        viewModelScope.launch {
            playlistDao.deletePlaylistById(playlist.id)
            channelDao.deleteChannelsByPlaylist(playlist.id)
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
                showToast("${playlist.name} 업데이트 완료")
            } catch (e: Exception) {
                showToast("${playlist.name} 업데이트 실패")
                e.printStackTrace()
            }
        }
    }

    fun refreshAllPlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentPlaylists = _playlists.value
            var successCount = 0
            var failCount = 0
            
            currentPlaylists.forEach { playlist ->
                try {
                    val content = URL(playlist.url).readText()
                    val channels = M3uParser.parse(content, playlist.id)
                    channelDao.deleteChannelsByPlaylist(playlist.id)
                    channelDao.insertChannels(channels)
                    playlistDao.insertPlaylist(playlist.copy(channelCount = channels.size))
                    successCount++
                } catch (e: Exception) {
                    failCount++
                }
            }
            
            showToast("전체 업데이트 완료 (성공: $successCount, 실패: $failCount)")
        }
    }

    private suspend fun showToast(message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
        }
    }
}
