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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.minimaltv.ui.TvViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: TvViewModel) {
    val settingsManager = viewModel.settingsManager
    var showEpgDialog by remember { mutableStateOf(false) }
    var epgUrlText by remember { mutableStateOf(settingsManager.epgUrl.value) }

    if (showEpgDialog) {
        AlertDialog(
            onDismissRequest = { showEpgDialog = false },
            title = { Text("EPG 소스 설정") },
            text = {
                OutlinedTextField(
                    value = epgUrlText,
                    onValueChange = { epgUrlText = it },
                    label = { Text("XMLTV URL 주소") },
                    placeholder = { Text("http://example.com/epg.xml") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    settingsManager.setEpgUrl(epgUrlText)
                    showEpgDialog = false
                }) { Text("저장") }
            },
            dismissButton = {
                TextButton(onClick = { showEpgDialog = false }) { Text("취소") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("설정", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            item {
                SettingsSectionHeader("재생 및 성능")
                SettingsToggleItem(
                    title = "하드웨어 가속",
                    description = "재생 성능 향상을 위해 GPU를 사용합니다.",
                    icon = Icons.Default.Settings,
                    checked = settingsManager.isHardwareAccelerationEnabled.value,
                    onCheckedChange = { settingsManager.setHardwareAcceleration(it) }
                )
                
                SettingsToggleItem(
                    title = "EPG 정보 표시",
                    description = "채널 목록과 플레이어에 방송 편성 정보를 표시합니다.",
                    icon = Icons.Default.Info,
                    checked = settingsManager.isEpgEnabled.value,
                    onCheckedChange = { settingsManager.setEpgEnabled(it) }
                )
                
                if (settingsManager.isEpgEnabled.value) {
                    SettingsNavigationItem(
                        title = "EPG 소스 설정",
                        description = settingsManager.epgUrl.value.ifEmpty { "XMLTV 형식의 EPG URL을 관리합니다." },
                        icon = Icons.AutoMirrored.Filled.List,
                        onClick = { showEpgDialog = true }
                    )
                }

                SettingsSectionHeader("정보")
                ListItem(
                    headlineContent = { Text("버전 정보") },
                    supportingContent = { Text("최신 버전 사용 중 (v1.0.0)") },
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
