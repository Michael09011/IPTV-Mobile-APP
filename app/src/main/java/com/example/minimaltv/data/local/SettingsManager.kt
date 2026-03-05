package com.example.minimaltv.data.local

import android.content.Context
import androidx.compose.runtime.mutableStateOf

class SettingsManager(context: Context) {
    private val prefs = context.getSharedPreferences("minimal_tv_settings", Context.MODE_PRIVATE)

    var isHardwareAccelerationEnabled = mutableStateOf(prefs.getBoolean("hw_accel", true))
    var isEpgEnabled = mutableStateOf(prefs.getBoolean("epg_enabled", true))
    var epgUrl = mutableStateOf(prefs.getString("epg_url", "") ?: "")

    fun setHardwareAcceleration(enabled: Boolean) {
        isHardwareAccelerationEnabled.value = enabled
        prefs.edit().putBoolean("hw_accel", enabled).apply()
    }

    fun setEpgEnabled(enabled: Boolean) {
        isEpgEnabled.value = enabled
        prefs.edit().putBoolean("epg_enabled", enabled).apply()
    }

    fun setEpgUrl(url: String) {
        epgUrl.value = url
        prefs.edit().putString("epg_url", url).apply()
    }
}
