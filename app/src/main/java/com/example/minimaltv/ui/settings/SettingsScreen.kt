package com.example.minimaltv.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.minimaltv.R
import com.example.minimaltv.data.local.ThemeMode
import com.example.minimaltv.ui.TvViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: TvViewModel) {
    val settingsManager = viewModel.settingsManager
    var showEpgDialog by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var epgUrlText by remember { mutableStateOf(settingsManager.epgUrl.value) }

    // EPG 소스 설정 다이얼로그
    if (showEpgDialog) {
        AlertDialog(
            onDismissRequest = { showEpgDialog = false },
            title = { Text(stringResource(R.string.settings_epg_source)) },
            text = {
                OutlinedTextField(
                    value = epgUrlText,
                    onValueChange = { epgUrlText = it },
                    label = { Text(stringResource(R.string.playlist_url)) },
                    placeholder = { Text("http://example.com/epg.xml") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    settingsManager.setEpgUrl(epgUrlText)
                    showEpgDialog = false
                }) { Text(stringResource(R.string.save)) }
            },
            dismissButton = {
                TextButton(onClick = { showEpgDialog = false }) { Text(stringResource(R.string.cancel)) }
            }
        )
    }

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
                
                if (settingsManager.isEpgEnabled.value) {
                    SettingsNavigationItem(
                        title = stringResource(R.string.settings_epg_source),
                        description = settingsManager.epgUrl.value.ifEmpty { stringResource(R.string.settings_epg_source) },
                        icon = Icons.AutoMirrored.Filled.List,
                        onClick = { showEpgDialog = true }
                    )
                }

                SettingsSectionHeader(stringResource(R.string.settings_info))
                ListItem(
                    headlineContent = { Text(stringResource(R.string.settings_version)) },
                    supportingContent = { Text(stringResource(R.string.settings_latest_version) + " (v1.0.0)") },
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
