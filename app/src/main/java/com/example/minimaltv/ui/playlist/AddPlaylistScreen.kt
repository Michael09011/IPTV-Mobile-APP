package com.example.minimaltv.ui.playlist

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.minimaltv.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaylistScreen(
    onClose: () -> Unit, 
    onAddUrl: (String, String) -> Unit,
    onAddLocalFile: (String, Uri) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(1) } // 0: Local, 1: URL
    var playlistName by remember { mutableStateOf("") }
    var playlistUrl by remember { mutableStateOf("") }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
            selectedTab = 0
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClose) {
                    Icon(Icons.Default.Close, contentDescription = stringResource(R.string.close))
                }
                Text(
                    text = stringResource(R.string.add_playlist_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(48.dp))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(stringResource(R.string.playlist_name_optional), style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = playlistName,
                onValueChange = { playlistName = it },
                placeholder = { Text(stringResource(R.string.playlist_name_placeholder)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(stringResource(R.string.add_method_title), style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(16.dp))

            AddOptionCard(
                title = stringResource(R.string.add_local_file),
                description = if (selectedFileUri != null) {
                    stringResource(R.string.file_selected, selectedFileUri?.path?.split("/")?.last() ?: "")
                } else {
                    stringResource(R.string.select_m3u_file)
                },
                iconRes = android.R.drawable.ic_menu_save,
                isSelected = selectedTab == 0,
                onClick = { 
                    selectedTab = 0
                    filePickerLauncher.launch("*/*")
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            AddOptionCard(
                title = stringResource(R.string.add_url),
                description = stringResource(R.string.add_url_desc),
                iconRes = android.R.drawable.ic_menu_send,
                isSelected = selectedTab == 1,
                onClick = { selectedTab = 1 }
            )

            if (selectedTab == 1) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(stringResource(R.string.playlist_url), style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = playlistUrl,
                    onValueChange = { playlistUrl = it },
                    placeholder = { Text("https://example.com/playlist.m3u") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onClose,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(R.string.cancel), color = MaterialTheme.colorScheme.onSurface)
                }
                Button(
                    onClick = { 
                        val finalName = playlistName.ifEmpty { 
                            if (selectedTab == 1) "URL Playlist" else "Local Playlist" 
                        }
                        if (selectedTab == 1) {
                            onAddUrl(finalName, playlistUrl)
                        } else {
                            selectedFileUri?.let { onAddLocalFile(finalName, it) }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    enabled = (selectedTab == 1 && playlistUrl.isNotEmpty()) || (selectedTab == 0 && selectedFileUri != null)
                ) {
                    Text(stringResource(R.string.add_playlist_confirm))
                }
            }
        }
    }
}

@Composable
fun AddOptionCard(
    title: String,
    description: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f) else Color.Transparent

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .border(1.dp, borderColor, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(painterResource(iconRes), contentDescription = null, tint = borderColor)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
            }
            if (isSelected) {
                RadioButton(selected = true, onClick = null)
            }
        }
    }
}
