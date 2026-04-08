package com.example.minimaltv.ui.playlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.minimaltv.data.local.AppDatabase
import com.example.minimaltv.data.model.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecentChannelsViewModel(application: Application) : AndroidViewModel(application) {
    private val channelDao = AppDatabase.getDatabase(application).channelDao()

    private val _recentChannels = MutableStateFlow<List<Channel>>(emptyList())
    val recentChannels: StateFlow<List<Channel>> = _recentChannels.asStateFlow()

    init {
        viewModelScope.launch {
            channelDao.getRecentChannels().collect {
                _recentChannels.value = it
            }
        }
    }
}
