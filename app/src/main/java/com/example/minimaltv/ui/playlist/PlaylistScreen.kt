package com.example.minimaltv.ui.playlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.minimaltv.R
import com.example.minimaltv.data.model.Playlist
import com.example.minimaltv.data.model.Channel
import com.example.minimaltv.ui.TvViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    viewModel: TvViewModel,
    onAddClick: () -> Unit,
    onPlaylistClick: (Playlist) -> Unit,
    onChannelClick: (Channel, Boolean) -> Unit,
    onDeletePlaylist: (Playlist) -> Unit,
    onRefreshPlaylist: (Playlist) -> Unit,
    onEditPlaylist: (Playlist, String, String) -> Unit, // 파라미터 확장
    onMovePlaylist: (Playlist, Boolean) -> Unit,
    onRefreshAll: () -> Unit
) {
    val playlists by viewModel.playlists.collectAsState()
    val recentChannels by viewModel.recentChannels.collectAsState()
    val context = LocalContext.current
    
    var showTopMenu by remember { mutableStateOf(false) }
    var playlistToEdit by remember { mutableStateOf<Playlist?>(null) }
    var editName by remember { mutableStateOf("") }
    var editEpgUrl by remember { mutableStateOf("") }

    if (playlistToEdit != null) {
        AlertDialog(
            onDismissRequest = { playlistToEdit = null },
            title = { Text("플레이리스트 편집") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text(stringResource(R.string.playlist_name)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = editEpgUrl,
                        onValueChange = { editEpgUrl = it },
                        label = { Text("EPG URL") },
                        placeholder = { Text("http://example.com/epg.xml") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    onEditPlaylist(playlistToEdit!!, editName, editEpgUrl)
                    playlistToEdit = null
                }) { Text(stringResource(R.string.save)) }
            },
            dismissButton = {
                TextButton(onClick = { playlistToEdit = null }) { Text(stringResource(R.string.cancel)) }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    val logoResId = remember(context) {
                        val id = context.resources.getIdentifier("ic_app_logo", "drawable", context.packageName)
                        if (id != 0) id else android.R.drawable.ic_menu_gallery
                    }
                    Surface(
                        modifier = Modifier.size(36.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Image(
                            painter = painterResource(id = logoResId),
                            contentDescription = stringResource(R.string.app_name),
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                },
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
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (recentChannels.isNotEmpty()) {
                item {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text(
                            text = stringResource(R.string.recent_channels_title),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(end = 16.dp)
                        ) {
                            items(recentChannels) { channel ->
                                RecentChannelItem(channel = channel, onClick = { onChannelClick(channel, false) })
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.playlist_saved_title), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(
                        text = stringResource(R.string.playlist_items_count, playlists.size), 
                        style = MaterialTheme.typography.bodySmall, 
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

            if (playlists.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.playlist_empty), color = MaterialTheme.colorScheme.outline)
                    }
                }
            } else {
                itemsIndexed(playlists) { index, playlist ->
                    PlaylistCard(
                        playlist = playlist,
                        isFirst = index == 0,
                        isLast = index == playlists.size - 1,
                        onClick = { onPlaylistClick(playlist) },
                        onDelete = { onDeletePlaylist(playlist) },
                        onRefresh = { onRefreshPlaylist(playlist) },
                        onEdit = { 
                            editName = playlist.name
                            editEpgUrl = playlist.epgUrl
                            playlistToEdit = playlist 
                        },
                        onMoveUp = { onMovePlaylist(playlist, true) },
                        onMoveDown = { onMovePlaylist(playlist, false) }
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun PlaylistCard(
    playlist: Playlist, 
    isFirst: Boolean,
    isLast: Boolean,
    onClick: () -> Unit, 
    onDelete: () -> Unit, 
    onRefresh: () -> Unit, 
    onEdit: () -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit
) {
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = onMoveUp, enabled = !isFirst, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.KeyboardArrowUp, contentDescription = null, tint = if(isFirst) Color.Gray else MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onMoveDown, enabled = !isLast, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, tint = if(isLast) Color.Gray else MaterialTheme.colorScheme.primary)
                }
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
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
                if (playlist.epgUrl.isNotEmpty()) {
                    Text(text = "EPG: ${playlist.epgUrl}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
            Box {
                IconButton(onClick = { showItemMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.common_options), tint = MaterialTheme.colorScheme.outline)
                }
                DropdownMenu(expanded = showItemMenu, onDismissRequest = { showItemMenu = false }) {
                    DropdownMenuItem(
                        text = { Text("편집") },
                        onClick = { onEdit(); showItemMenu = false },
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

@Composable
fun RecentChannelItem(channel: Channel, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = channel.thumbnail,
            contentDescription = channel.name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Fit,
            error = painterResource(id = android.R.drawable.ic_menu_gallery)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = channel.name,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}
