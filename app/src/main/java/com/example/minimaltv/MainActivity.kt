package com.example.minimaltv

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.minimaltv.ui.TvViewModel
import com.example.minimaltv.ui.favorites.FavoritesScreen
import com.example.minimaltv.ui.playlist.PlaylistScreen
import com.example.minimaltv.ui.playlist.AddPlaylistScreen
import com.example.minimaltv.ui.channel.ChannelListScreen
import com.example.minimaltv.ui.player.VideoPlayerScreen
import com.example.minimaltv.ui.settings.SettingsScreen
import com.example.minimaltv.ui.theme.MinimalTVTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        
        setContent {
            MinimalTVTheme {
                MainScreen()
            }
        }
    }
}

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Playlist : Screen("playlist", "재생 목록", Icons.Default.List)
    object Favorites : Screen("favorites", "즐겨찾기", Icons.Default.Favorite)
    object Settings : Screen("settings", "설정", Icons.Default.Settings)
    object AddPlaylist : Screen("add_playlist", "추가", Icons.Default.List)
    object ChannelList : Screen("channel_list/{playlistId}/{playlistName}", "채널 목록", Icons.Default.List)
    object Player : Screen("player", "플레이어", Icons.Default.List)
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val viewModel: TvViewModel = viewModel()
    val playlists by viewModel.playlists.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val selectedChannels by viewModel.selectedChannels.collectAsState()
    val currentPlayingChannel by viewModel.currentPlayingChannel.collectAsState()

    val items = listOf(Screen.Playlist, Screen.Favorites, Screen.Settings)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val showBottomBar = items.any { it.route == currentDestination?.route }
            
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.95f),
                    tonalElevation = 0.dp
                ) {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Playlist.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screen.Playlist.route) {
                PlaylistScreen(
                    playlists = playlists,
                    onAddClick = { navController.navigate(Screen.AddPlaylist.route) },
                    onPlaylistClick = { playlist ->
                        navController.navigate("channel_list/${playlist.id}/${playlist.name}")
                    },
                    onDeletePlaylist = { viewModel.deletePlaylist(it) },
                    onRefreshPlaylist = { viewModel.refreshPlaylist(it) },
                    onRenamePlaylist = { playlist, newName -> viewModel.renamePlaylist(playlist, newName) },
                    onRefreshAll = { /* 구현 */ }
                )
            }
            composable(Screen.AddPlaylist.route) {
                AddPlaylistScreen(
                    onClose = { navController.popBackStack() },
                    onAddUrl = { name, url -> 
                        viewModel.addPlaylistFromUrl(name, url)
                        navController.popBackStack()
                    },
                    onAddLocalFile = { name, uri -> 
                        viewModel.addPlaylistFromLocalFile(name, uri)
                        navController.popBackStack()
                    }
                )
            }
            composable(
                route = Screen.ChannelList.route,
                arguments = listOf(
                    navArgument("playlistId") { type = NavType.StringType },
                    navArgument("playlistName") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val playlistId = backStackEntry.arguments?.getString("playlistId") ?: ""
                val playlistName = backStackEntry.arguments?.getString("playlistName") ?: "채널"
                
                LaunchedEffect(playlistId) {
                    viewModel.loadChannelsForPlaylist(playlistId)
                }

                ChannelListScreen(
                    categoryName = playlistName,
                    channels = selectedChannels,
                    onBackClick = { navController.popBackStack() },
                    onChannelClick = { channel ->
                        viewModel.selectChannel(channel)
                        navController.navigate(Screen.Player.route)
                    },
                    onFavoriteToggle = { viewModel.toggleFavorite(it) }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    favoriteChannels = favorites,
                    onChannelClick = { channel ->
                        viewModel.selectChannel(channel)
                        navController.navigate(Screen.Player.route)
                    },
                    onFavoriteToggle = { viewModel.toggleFavorite(it) }
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(viewModel = viewModel)
            }
            composable(Screen.Player.route) {
                VideoPlayerScreen(
                    channel = currentPlayingChannel,
                    hardwareAcceleration = viewModel.settingsManager.isHardwareAccelerationEnabled.value,
                    onBackClick = { navController.popBackStack() },
                    onNextChannel = { viewModel.nextChannel() },
                    onPrevChannel = { viewModel.prevChannel() }
                )
            }
        }
    }
}
