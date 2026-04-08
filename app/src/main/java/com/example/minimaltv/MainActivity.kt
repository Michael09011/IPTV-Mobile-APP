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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.minimaltv.data.local.ThemeMode
import com.example.minimaltv.ui.TvViewModel
import com.example.minimaltv.ui.favorites.FavoritesScreen
import com.example.minimaltv.ui.favorites.FavoritesViewModel
import com.example.minimaltv.ui.playlist.PlaylistScreen
import com.example.minimaltv.ui.playlist.AddPlaylistScreen
import com.example.minimaltv.ui.playlist.RecentChannelsViewModel
import com.example.minimaltv.ui.channel.ChannelListScreen
import com.example.minimaltv.ui.player.VideoPlayerScreen
import com.example.minimaltv.ui.settings.SettingsScreen
import com.example.minimaltv.ui.theme.MinimalTVTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        
        setContent {
            val tvViewModel: TvViewModel = viewModel()
            val themeMode by tvViewModel.settingsManager.themeMode
            
            val darkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
            }

            MinimalTVTheme(darkTheme = darkTheme) {
                MainScreen(tvViewModel)
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
fun MainScreen(tvViewModel: TvViewModel) {
    val navController = rememberNavController()
    val favoritesViewModel: FavoritesViewModel = viewModel()
    val recentChannelsViewModel: RecentChannelsViewModel = viewModel()
    
    val favorites by favoritesViewModel.favorites.collectAsState()
    val recentChannels by recentChannelsViewModel.recentChannels.collectAsState()

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
                    tvViewModel.clearSearchQuery()
                }
                PlaylistScreen(
                    tvViewModel = tvViewModel,
                    recentChannels = recentChannels,
                    onAddClick = { navController.navigate(Screen.AddPlaylist.route) },
                    onPlaylistClick = { playlist ->
                        navController.navigate("channel_list/${playlist.id}/${playlist.name}")
                    },
                    onChannelClick = { channel, enableSidebar ->
                        tvViewModel.selectChannel(channel, enableSidebar)
                        navController.navigate(Screen.Player.route)
                    },
                    onDeletePlaylist = { tvViewModel.deletePlaylist(it) },
                    onRefreshPlaylist = { tvViewModel.refreshPlaylist(it) },
                    onEditPlaylist = { playlist, name, epgUrl -> 
                        tvViewModel.editPlaylist(playlist, name, epgUrl) 
                    },
                    onMovePlaylist = { playlist, up -> tvViewModel.movePlaylist(playlist, up) },
                    onRefreshAll = { tvViewModel.refreshAllPlaylists() }
                )
            }
            composable(Screen.AddPlaylist.route) {
                AddPlaylistScreen(
                    onClose = { navController.popBackStack() },
                    onAddUrl = { name, url, epgUrl -> 
                        tvViewModel.addPlaylistFromUrl(name, url, epgUrl)
                        navController.popBackStack()
                    },
                    onAddLocalFile = { name, uri -> 
                        tvViewModel.addPlaylistFromLocalFile(name, uri)
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
                    tvViewModel.loadChannelsForPlaylist(playlistId)
                }

                ChannelListScreen(
                    viewModel = tvViewModel,
                    categoryName = playlistName,
                    onBackClick = { navController.popBackStack() },
                    onChannelClick = { channel ->
                        tvViewModel.selectChannel(channel, true)
                        navController.navigate(Screen.Player.route)
                    },
                    onFavoriteToggle = { tvViewModel.toggleFavorite(it) }
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    favoriteChannels = favorites,
                    onChannelClick = { channel ->
                        tvViewModel.selectChannel(channel, false)
                        navController.navigate(Screen.Player.route)
                    },
                    onFavoriteToggle = { favoritesViewModel.toggleFavorite(it) }
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(viewModel = tvViewModel)
            }
            composable(Screen.Player.route) {
                VideoPlayerScreen(
                    viewModel = tvViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
