package com.example.minimaltv.data.local

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}

class SettingsManager(context: Context) {
    private val prefs = context.getSharedPreferences("minimal_tv_settings", Context.MODE_PRIVATE)

    var isHardwareAccelerationEnabled = mutableStateOf(prefs.getBoolean("hw_accel", true))
    var isEpgEnabled = mutableStateOf(prefs.getBoolean("epg_enabled", true))
    var epgUrl = mutableStateOf(prefs.getString("epg_url", "") ?: "")
    var selectedLanguage = mutableStateOf(prefs.getString("app_language", "ko") ?: "ko")
    var themeMode = mutableStateOf(ThemeMode.valueOf(prefs.getString("theme_mode", ThemeMode.SYSTEM.name) ?: ThemeMode.SYSTEM.name))

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

    fun setLanguage(languageCode: String) {
        selectedLanguage.value = languageCode
        prefs.edit().putString("app_language", languageCode).apply()
        
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    fun setThemeMode(mode: ThemeMode) {
        themeMode.value = mode
        prefs.edit().putString("theme_mode", mode.name).apply()
    }
}
