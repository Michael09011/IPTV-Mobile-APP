package com.example.minimaltv.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.minimaltv.data.local.AppDatabase
import com.example.minimaltv.data.model.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val channelDao = AppDatabase.getDatabase(application).channelDao()

    private val _favorites = MutableStateFlow<List<Channel>>(emptyList())
    val favorites: StateFlow<List<Channel>> = _favorites.asStateFlow()

    init {
        viewModelScope.launch {
            channelDao.getFavoriteChannels().collect {
                _favorites.value = it
            }
        }
    }

    fun toggleFavorite(channel: Channel) {
        viewModelScope.launch(Dispatchers.IO) {
            val updated = channel.copy(isFavorite = !channel.isFavorite)
            channelDao.updateChannel(updated)
        }
    }
}
