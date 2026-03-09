package com.example.minimaltv.ui

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.minimaltv.data.local.AppDatabase
import com.example.minimaltv.data.local.SettingsManager
import com.example.minimaltv.data.local.UpdateInterval
import com.example.minimaltv.data.model.Playlist
import com.example.minimaltv.data.model.Channel
import com.example.minimaltv.data.model.EpgProgram
import com.example.minimaltv.data.parser.M3uParser
import com.example.minimaltv.data.parser.EpgParser
import com.example.minimaltv.data.worker.UpdateWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.UUID
import java.util.concurrent.TimeUnit

class TvViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val playlistDao = database.playlistDao()
    private val channelDao = database.channelDao()
    val settingsManager = SettingsManager(application)
    private val workManager = WorkManager.getInstance(application)

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

    private val _isSidebarEnabled = MutableStateFlow(true)
    val isSidebarEnabled: StateFlow<Boolean> = _isSidebarEnabled.asStateFlow()

    private val epgCache = mutableMapOf<String, List<EpgProgram>>()
    private var channelLoadJob: Job? = null

    init {
        loadData()
        scheduleUpdateWorker(settingsManager.updateInterval.value)
    }

    private fun loadData() {
        viewModelScope.launch {
            playlistDao.getAllPlaylists().collect { _playlists.value = it }
        }
        viewModelScope.launch {
            channelDao.getFavoriteChannels().collect { _favorites.value = it }
        }
        viewModelScope.launch {
            channelDao.getRecentChannels().collect { _recentChannels.value = it }
        }
    }

    fun setUpdateInterval(interval: UpdateInterval) {
        settingsManager.setUpdateInterval(interval)
        scheduleUpdateWorker(interval)
    }

    private fun scheduleUpdateWorker(interval: UpdateInterval) {
        workManager.cancelUniqueWork("auto_update_work")
        if (interval == UpdateInterval.OFF) return

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val updateRequest = PeriodicWorkRequestBuilder<UpdateWorker>(
            interval.hours, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "auto_update_work",
            ExistingPeriodicWorkPolicy.UPDATE,
            updateRequest
        )
    }

    fun loadChannelsForPlaylist(playlistId: String) {
        if (_currentPlaylistId.value == playlistId && _selectedChannels.value.isNotEmpty()) return
        
        _currentPlaylistId.value = playlistId
        _selectedChannels.value = emptyList()
        channelLoadJob?.cancel()
        
        channelLoadJob = viewModelScope.launch {
            channelDao.getChannelsByPlaylist(playlistId).collect { channels ->
                val playlist = _playlists.value.find { it.id == playlistId }
                val updatedChannels = if (settingsManager.isEpgEnabled.value && playlist != null && playlist.epgUrl.isNotEmpty()) {
                    matchEpg(channels, playlist)
                } else {
                    channels
                }
                _selectedChannels.value = updatedChannels
            }
        }
    }

    private suspend fun matchEpg(channels: List<Channel>, playlist: Playlist): List<Channel> {
        val epgPrograms = epgCache[playlist.id] ?: withContext(Dispatchers.IO) {
            try {
                val stream = URL(playlist.epgUrl).openStream()
                val parsed = EpgParser.parse(stream)
                epgCache[playlist.id] = parsed
                parsed
            } catch (e: Exception) { emptyList() }
        }
        if (epgPrograms.isEmpty()) return channels
        val currentTime = System.currentTimeMillis()
        return channels.map { channel ->
            val currentProg = epgPrograms.find { it.channelId == channel.name && currentTime in it.startTime..it.endTime }
            channel.copy(currentProgram = currentProg?.title)
        }
    }

    fun nextChannel() {
        if (!_isSidebarEnabled.value) return
        val current = _currentPlayingChannel.value ?: return
        val list = _selectedChannels.value
        if (list.isNotEmpty()) {
            val index = list.indexOfFirst { it.id == current.id }
            if (index != -1 && index < list.size - 1) {
                selectChannel(list[index + 1], true)
            }
        }
    }

    fun prevChannel() {
        if (!_isSidebarEnabled.value) return
        val current = _currentPlayingChannel.value ?: return
        val list = _selectedChannels.value
        if (list.isNotEmpty()) {
            val index = list.indexOfFirst { it.id == current.id }
            if (index > 0) {
                selectChannel(list[index - 1], true)
            }
        }
    }

    fun movePlaylist(playlist: Playlist, up: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = _playlists.value.toMutableList()
            val index = list.indexOfFirst { it.id == playlist.id }
            if (index == -1) return@launch
            val targetIndex = if (up) index - 1 else index + 1
            if (targetIndex in list.indices) {
                val other = list[targetIndex]
                list[index] = other.copy(displayOrder = playlist.displayOrder)
                list[targetIndex] = playlist.copy(displayOrder = other.displayOrder)
                playlistDao.updatePlaylists(list)
            }
        }
    }

    fun selectChannel(channel: Channel, enableSidebar: Boolean = true) {
        _currentPlayingChannel.value = channel
        _isSidebarEnabled.value = enableSidebar
        if (enableSidebar) loadChannelsForPlaylist(channel.playlistId)
        viewModelScope.launch(Dispatchers.IO) {
            channelDao.updateChannel(channel.copy(lastWatched = System.currentTimeMillis()))
        }
    }

    fun addPlaylistFromUrl(name: String, url: String, epgUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val playlistId = UUID.randomUUID().toString()
                val content = URL(url).readText()
                val channels = M3uParser.parse(content, playlistId)
                val maxOrder = playlistDao.getMaxOrder() ?: 0
                val playlist = Playlist(id = playlistId, name = name, url = url, epgUrl = epgUrl, channelCount = channels.size, displayOrder = maxOrder + 1)
                playlistDao.insertPlaylist(playlist)
                channelDao.insertChannels(channels)
                showToast("플레이리스트 추가 완료")
            } catch (e: Exception) { showToast("추가 실패") }
        }
    }

    fun addPlaylistFromLocalFile(name: String, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val playlistId = UUID.randomUUID().toString()
                val content = getApplication<Application>().contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }
                if (content != null) {
                    val channels = M3uParser.parse(content, playlistId)
                    val maxOrder = playlistDao.getMaxOrder() ?: 0
                    val playlist = Playlist(id = playlistId, name = name, url = uri.toString(), channelCount = channels.size, displayOrder = maxOrder + 1)
                    playlistDao.insertPlaylist(playlist)
                    channelDao.insertChannels(channels)
                    showToast("파일 추가 완료")
                }
            } catch (e: Exception) { showToast("파일 추가 실패") }
        }
    }

    fun editPlaylist(playlist: Playlist, newName: String, newEpgUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedPlaylist = playlist.copy(name = newName, epgUrl = newEpgUrl)
            playlistDao.insertPlaylist(updatedPlaylist)
            // EPG 캐시 무효화
            epgCache.remove(playlist.id)
        }
    }

    fun clearSearchQuery() { _searchQuery.value = "" }
    fun setSearchQuery(query: String) { _searchQuery.value = query }
    private suspend fun showToast(message: String) { withContext(Dispatchers.Main) { Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show() } }
    fun deletePlaylist(playlist: Playlist) { viewModelScope.launch(Dispatchers.IO) { playlistDao.deletePlaylistById(playlist.id); channelDao.deleteChannelsByPlaylist(playlist.id) } }
    fun toggleFavorite(channel: Channel) { viewModelScope.launch(Dispatchers.IO) { val updated = channel.copy(isFavorite = !channel.isFavorite); channelDao.updateChannel(updated); if (_currentPlayingChannel.value?.id == channel.id) _currentPlayingChannel.value = updated } }
    fun refreshPlaylist(playlist: Playlist) { viewModelScope.launch(Dispatchers.IO) { try { val content = URL(playlist.url).readText(); val channels = M3uParser.parse(content, playlist.id); channelDao.deleteChannelsByPlaylist(playlist.id); channelDao.insertChannels(channels); playlistDao.insertPlaylist(playlist.copy(channelCount = channels.size)) } catch (e: Exception) { } } }
    fun refreshAllPlaylists() { viewModelScope.launch(Dispatchers.IO) { _playlists.value.forEach { refreshPlaylist(it) } } }
}
