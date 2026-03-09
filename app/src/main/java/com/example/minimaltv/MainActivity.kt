package com.example.minimaltv

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.minimaltv.data.local.ThemeMode
import com.example.minimaltv.ui.TvViewModel
import com.example.minimaltv.ui.favorites.FavoritesScreen
import com.example.minimaltv.ui.playlist.PlaylistScreen
import com.example.minimaltv.ui.playlist.AddPlaylistScreen
import com.example.minimaltv.ui.channel.ChannelListScreen
import com.example.minimaltv.ui.player.VideoPlayerScreen
import com.example.minimaltv.ui.settings.SettingsScreen
import com.example.minimaltv.ui.theme.MinimalTVTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        
        setContent {
            val viewModel: TvViewModel = viewModel()
            val themeMode by viewModel.settingsManager.themeMode
            
            val darkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            MinimalTVTheme(darkTheme = darkTheme) {
                MainScreen(viewModel)
            }
        }
    }
}

sealed class Screen(val route: String, val titleResId: Int, val icon: ImageVector) {
    object Playlist : Screen("playlist", R.string.nav_playlist, Icons.AutoMirrored.Filled.List)
    object Favorites : Screen("favorites", R.string.nav_favorites, Icons.Default.Favorite)
    object Settings : Screen("settings", R.string.nav_settings, Icons.Default.Settings)
    object AddPlaylist : Screen("add_playlist", R.string.add_playlist, Icons.AutoMirrored.Filled.List)
    object ChannelList : Screen("channel_list/{playlistId}/{playlistName}", R.string.app_name, Icons.AutoMirrored.Filled.List)
    object Player : Screen("player", R.string.app_name, Icons.AutoMirrored.Filled.List)
}

@Composable
fun MainScreen(viewModel: TvViewModel) {
    val navController = rememberNavController()
    val favorites by viewModel.favorites.collectAsState()

    val items = listOf(Screen.Playlist, Screen.Favorites, Screen.Settings)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            val showBottomBar = items.any { it.route == currentRoute }
            
            if (showBottomBar) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.95f),
                    tonalElevation = 0.dp
                ) {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(stringResource(screen.titleResId)) },
                            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == screen.route } == true,
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
                .padding(if (currentRoute == Screen.Player.route) PaddingValues(0.dp) else innerPadding)
        ) {
            composable(Screen.Playlist.route) {
                LaunchedEffect(Unit) {
                    viewModel.clearSearchQuery()
                }
                PlaylistScreen(
                    viewModel = viewModel,
                    onAddClick = { navController.navigate(Screen.AddPlaylist.route) },
                    onPlaylistClick = { playlist ->
                        navController.navigate("channel_list/${playlist.id}/${playlist.name}")
                    },
                    // 최근 시청 채널 등에서 직접 재생 시 (사이드바 비활성화 옵션 포함)
                    onChannelClick = { channel, enableSidebar ->
                        viewModel.selectChannel(channel, enableSidebar)
                        navController.navigate(Screen.Player.route)
                    },
                    onDeletePlaylist = { viewModel.deletePlaylist(it) },
                    onRefreshPlaylist = { viewModel.refreshPlaylist(it) },
                    onEditPlaylist = { playlist, name, epgUrl -> 
                        viewModel.editPlaylist(playlist, name, epgUrl) 
                    },
                    onMovePlaylist = { playlist, up -> viewModel.movePlaylist(playlist, up) },
                    onRefreshAll = { viewModel.refreshAllPlaylists() }
                )
            }
            composable(Screen.AddPlaylist.route) {
                AddPlaylistScreen(
                    onClose = { navController.popBackStack() },
                    onAddUrl = { name, url, epgUrl -> 
                        viewModel.addPlaylistFromUrl(name, url, epgUrl)
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
                    viewModel = viewModel,
                    categoryName = playlistName,
                    onBackClick = { navController.popBackStack() },
                    onChannelClick = { channel ->
                        viewModel.selectChannel(channel, true)
                        navController.navigate(Screen.Player.route)
                    },
                    onFavoriteToggle = { viewModel.toggleFavorite(it) }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    favoriteChannels = favorites,
                    // 즐겨찾기 재생 시 사이드바 비활성화
                    onChannelClick = { channel ->
                        viewModel.selectChannel(channel, false)
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
                    viewModel = viewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
