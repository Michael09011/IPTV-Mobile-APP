package com.example.minimaltv.ui.channel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.minimaltv.R
import com.example.minimaltv.data.model.Channel
import com.example.minimaltv.ui.TvViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelListScreen(
    viewModel: TvViewModel,
    categoryName: String,
    onBackClick: () -> Unit,
    onChannelClick: (Channel) -> Unit,
    onFavoriteToggle: (Channel) -> Unit
) {
    val channels by viewModel.selectedChannels.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    
    var showMenu by remember { mutableStateOf(false) }
    val allCategoryLabel = stringResource(R.string.channel_category_all)
    var selectedCategory by remember { mutableStateOf(allCategoryLabel) }
    var isSearchMode by remember { mutableStateOf(searchQuery.isNotEmpty()) }
    val focusManager = LocalFocusManager.current
    
    val categories = remember(channels, allCategoryLabel) {
        listOf(allCategoryLabel) + channels.map { it.category }.distinct().sorted()
    }

    val categoryFiltered = remember(selectedCategory, channels, allCategoryLabel) {
        if (selectedCategory == allCategoryLabel) channels 
        else channels.filter { it.category == selectedCategory }
    }

    val filteredChannels = remember(searchQuery, categoryFiltered) {
        if (searchQuery.isEmpty()) categoryFiltered
        else categoryFiltered.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    if (isSearchMode) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { viewModel.setSearchQuery(it) },
                            placeholder = { Text(stringResource(R.string.channel_search_placeholder)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
                        )
                    } else {
                        Column {
                            Text(categoryName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            if (selectedCategory != allCategoryLabel) {
                                Text(selectedCategory, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isSearchMode && searchQuery.isEmpty()) {
                            isSearchMode = false
                        } else if (isSearchMode) {
                            viewModel.setSearchQuery("")
                        } else {
                            onBackClick()
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.channel_back))
                    }
                },
                actions = {
                    if (!isSearchMode) {
                        IconButton(onClick = { isSearchMode = true }) {
                            Icon(Icons.Default.Search, contentDescription = stringResource(R.string.favorites_search))
                        }
                        Box {
                            IconButton(onClick = { showMenu = true }) {
                                Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.common_more))
                            }
                            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                                Text(stringResource(R.string.channel_filter_title), modifier = Modifier.padding(16.dp, 8.dp), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
                                categories.forEach { category ->
                                    DropdownMenuItem(
                                        text = { Text(category) },
                                        onClick = { 
                                            selectedCategory = category
                                            showMenu = false 
                                        },
                                        trailingIcon = {
                                            if (selectedCategory == category) Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                        }
                                    )
                                }
                            }
                        }
                    } else {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.setSearchQuery("") }) {
                                Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.channel_clear_search))
                            }
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (searchQuery.isEmpty()) {
                        stringResource(R.string.channel_total_count, filteredChannels.size)
                    } else {
                        stringResource(R.string.channel_search_result, searchQuery, filteredChannels.size)
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredChannels, key = { it.id }) { channel ->
                    ChannelListItem(
                        channel = channel,
                        onClick = { onChannelClick(channel) },
                        onFavoriteToggle = { onFavoriteToggle(channel) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
                }
            }
        }
    }
}

@Composable
fun ChannelListItem(channel: Channel, onClick: () -> Unit, onFavoriteToggle: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = channel.thumbnail,
            contentDescription = channel.name,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Fit,
            error = painterResource(id = android.R.drawable.ic_menu_gallery),
            placeholder = painterResource(id = android.R.drawable.ic_menu_gallery)
        )

        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = channel.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(text = channel.category, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
        }
        IconButton(onClick = onFavoriteToggle) {
            Icon(
                imageVector = if (channel.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.channel_favorite_toggle),
                tint = if (channel.isFavorite) Color.Red else MaterialTheme.colorScheme.outline
            )
        }
    }
}
