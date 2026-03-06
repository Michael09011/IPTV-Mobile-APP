package com.example.minimaltv.ui.playlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.minimaltv.R
import com.example.minimaltv.data.model.Playlist

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    playlists: List<Playlist>,
    onAddClick: () -> Unit,
    onPlaylistClick: (Playlist) -> Unit,
    onDeletePlaylist: (Playlist) -> Unit,
    onRefreshPlaylist: (Playlist) -> Unit,
    onRenamePlaylist: (Playlist, String) -> Unit,
    onRefreshAll: () -> Unit
) {
    var showTopMenu by remember { mutableStateOf(false) }
    var playlistToRename by remember { mutableStateOf<Playlist?>(null) }
    var newName by remember { mutableStateOf("") }

    if (playlistToRename != null) {
        AlertDialog(
            onDismissRequest = { playlistToRename = null },
            title = { Text(stringResource(R.string.rename_playlist)) },
            text = {
                OutlinedTextField(
                    value = newName,
                    onValueChange = { newName = it },
                    label = { Text(stringResource(R.string.playlist_name)) },
                    singleLine = true
                )
            },
            confirmButton = {
                Button(onClick = {
                    onRenamePlaylist(playlistToRename!!, newName)
                    playlistToRename = null
                }) { Text(stringResource(R.string.save)) }
            },
            dismissButton = {
                TextButton(onClick = { playlistToRename = null }) { Text(stringResource(R.string.cancel)) }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name), fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onAddClick) {
                        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_playlist))
                    }
                    Box {
                        IconButton(onClick = { showTopMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.common_more))
                        }
                        DropdownMenu(expanded = showTopMenu, onDismissRequest = { showTopMenu = false }) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.playlist_update_all)) },
                                onClick = { onRefreshAll(); showTopMenu = false },
                                leadingIcon = { Icon(Icons.Default.Refresh, contentDescription = null) }
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.playlist_saved_title), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(
                    text = stringResource(R.string.playlist_items_count, playlists.size), 
                    style = MaterialTheme.typography.bodyMedium, 
                    color = MaterialTheme.colorScheme.outline
                )
            }

            if (playlists.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.playlist_empty), color = MaterialTheme.colorScheme.outline)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(playlists) { playlist ->
                        PlaylistCard(
                            playlist = playlist,
                            onClick = { onPlaylistClick(playlist) },
                            onDelete = { onDeletePlaylist(playlist) },
                            onRefresh = { onRefreshPlaylist(playlist) },
                            onRename = { 
                                newName = playlist.name
                                playlistToRename = playlist 
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PlaylistCard(playlist: Playlist, onClick: () -> Unit, onDelete: () -> Unit, onRefresh: () -> Unit, onRename: () -> Unit) {
    var showItemMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(48.dp).background(Color.White, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.AutoMirrored.Filled.List, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = playlist.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(
                    text = stringResource(R.string.playlist_channels_count, playlist.channelCount), 
                    style = MaterialTheme.typography.bodySmall, 
                    color = MaterialTheme.colorScheme.outline
                )
                Text(text = playlist.url, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline, maxLines = 1)
            }
            Box {
                IconButton(onClick = { showItemMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.common_options), tint = MaterialTheme.colorScheme.outline)
                }
                DropdownMenu(expanded = showItemMenu, onDismissRequest = { showItemMenu = false }) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.rename_playlist)) },
                        onClick = { onRename(); showItemMenu = false },
                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.refresh_playlist)) },
                        onClick = { onRefresh(); showItemMenu = false },
                        leadingIcon = { Icon(Icons.Default.Refresh, contentDescription = null) }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.delete_playlist), color = Color.Red) },
                        onClick = { onDelete(); showItemMenu = false },
                        leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red) }
                    )
                }
            }
        }
    }
}
