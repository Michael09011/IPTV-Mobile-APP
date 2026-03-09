package com.example.minimaltv.ui.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.minimaltv.R
import com.example.minimaltv.data.local.ThemeMode
import com.example.minimaltv.data.local.UpdateInterval
import com.example.minimaltv.ui.TvViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: TvViewModel) {
    val settingsManager = viewModel.settingsManager
    val context = LocalContext.current
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showUpdateIntervalDialog by remember { mutableStateOf(false) }

    // 언어 설정 다이얼로그
    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text(stringResource(R.string.settings_language)) },
            text = {
                Column {
                    val languages = listOf("ko" to "한국어", "en" to "English", "ja" to "日本語")
                    languages.forEach { (code, name) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    settingsManager.setLanguage(code)
                                    showLanguageDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = settingsManager.selectedLanguage.value == code,
                                onClick = {
                                    settingsManager.setLanguage(code)
                                    showLanguageDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = name)
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }

    // 테마 설정 다이얼로그
    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = { Text(stringResource(R.string.settings_theme)) },
            text = {
                Column {
                    val themes = listOf(
                        ThemeMode.SYSTEM to stringResource(R.string.settings_theme_system),
                        ThemeMode.LIGHT to stringResource(R.string.settings_theme_light),
                        ThemeMode.DARK to stringResource(R.string.settings_theme_dark)
                    )
                    themes.forEach { (mode, name) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    settingsManager.setThemeMode(mode)
                                    showThemeDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = settingsManager.themeMode.value == mode,
                                onClick = {
                                    settingsManager.setThemeMode(mode)
                                    showThemeDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = name)
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }

    // 자동 업데이트 주기 설정 다이얼로그
    if (showUpdateIntervalDialog) {
        AlertDialog(
            onDismissRequest = { showUpdateIntervalDialog = false },
            title = { Text("자동 업데이트 주기") },
            text = {
                Column {
                    val intervals = listOf(
                        UpdateInterval.OFF to "안 함",
                        UpdateInterval.SIX_HOURS to "6시간마다",
                        UpdateInterval.TWELVE_HOURS to "12시간마다",
                        UpdateInterval.TWENTY_FOUR_HOURS to "24시간마다"
                    )
                    intervals.forEach { (interval, label) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setUpdateInterval(interval)
                                    showUpdateIntervalDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = settingsManager.updateInterval.value == interval,
                                onClick = {
                                    viewModel.setUpdateInterval(interval)
                                    showUpdateIntervalDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = label)
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.nav_settings), fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            item {
                SettingsSectionHeader(stringResource(R.string.settings_general))
                SettingsNavigationItem(
                    title = stringResource(R.string.settings_language),
                    description = when(settingsManager.selectedLanguage.value) {
                        "ko" -> "한국어"
                        "en" -> "English"
                        "ja" -> "日本語"
                        else -> "한국어"
                    },
                    icon = Icons.Default.Language,
                    onClick = { showLanguageDialog = true }
                )

                SettingsNavigationItem(
                    title = stringResource(R.string.settings_theme),
                    description = when(settingsManager.themeMode.value) {
                        ThemeMode.SYSTEM -> stringResource(R.string.settings_theme_system)
                        ThemeMode.LIGHT -> stringResource(R.string.settings_theme_light)
                        ThemeMode.DARK -> stringResource(R.string.settings_theme_dark)
                    },
                    icon = Icons.Default.Brightness4,
                    onClick = { showThemeDialog = true }
                )

                SettingsSectionHeader(stringResource(R.string.settings_playback))
                SettingsToggleItem(
                    title = stringResource(R.string.settings_hw_accel),
                    description = stringResource(R.string.settings_hw_accel_desc),
                    icon = Icons.Default.Settings,
                    checked = settingsManager.isHardwareAccelerationEnabled.value,
                    onCheckedChange = { settingsManager.setHardwareAcceleration(it) }
                )
                
                SettingsToggleItem(
                    title = stringResource(R.string.settings_epg),
                    description = stringResource(R.string.settings_epg_desc),
                    icon = Icons.Default.Info,
                    checked = settingsManager.isEpgEnabled.value,
                    onCheckedChange = { settingsManager.setEpgEnabled(it) }
                )

                SettingsNavigationItem(
                    title = "자동 업데이트 주기",
                    description = when(settingsManager.updateInterval.value) {
                        UpdateInterval.OFF -> "안 함"
                        UpdateInterval.SIX_HOURS -> "6시간마다"
                        UpdateInterval.TWELVE_HOURS -> "12시간마다"
                        UpdateInterval.TWENTY_FOUR_HOURS -> "24시간마다"
                    },
                    icon = Icons.Default.Sync,
                    onClick = { showUpdateIntervalDialog = true }
                )

                SettingsSectionHeader(stringResource(R.string.settings_info))
                ListItem(
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Michael09011/IPTV-Mobile-APP/releases"))
                        context.startActivity(intent)
                    },
                    headlineContent = { Text(stringResource(R.string.settings_version)) },
                    supportingContent = { Text(stringResource(R.string.settings_latest_version) + " (v1.0.2)") },
                    leadingContent = {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    },
                    trailingContent = {
                        Surface(color = Color(0xFF4CAF50), shape = androidx.compose.foundation.shape.CircleShape) {
                            Text(text = "Latest", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), color = Color.White, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                )
                
                SettingsNavigationItem(
                    title = stringResource(R.string.settings_release_site),
                    description = "GitHub Releases",
                    icon = Icons.AutoMirrored.Filled.OpenInNew,
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Michael09011/IPTV-Mobile-APP/releases"))
                        context.startActivity(intent)
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))
                
                Text(
                    text = "© 2026 Michael. All rights reserved.",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun SettingsToggleItem(
    title: String,
    description: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = { Text(title, fontWeight = FontWeight.Medium) },
        supportingContent = { Text(description, style = MaterialTheme.typography.bodySmall) },
        leadingContent = { 
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
                }
            }
        },
        trailingContent = {
            Switch(checked = checked, onCheckedChange = onCheckedChange)
        }
    )
}

@Composable
fun SettingsNavigationItem(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    ListItem(
        modifier = Modifier.clickable { onClick() },
        headlineContent = { Text(title, fontWeight = FontWeight.Medium) },
        supportingContent = { Text(description, style = MaterialTheme.typography.bodySmall, maxLines = 1) },
        leadingContent = {
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
                }
            }
        },
        trailingContent = {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
        }
    )
}
