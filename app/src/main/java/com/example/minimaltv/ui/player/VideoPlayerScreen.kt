package com.example.minimaltv.ui.player

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.view.WindowManager
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.minimaltv.R
import com.example.minimaltv.data.model.Channel
import com.example.minimaltv.player.ExoPlayerManager
import com.example.minimaltv.ui.TvViewModel

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(
    viewModel: TvViewModel,
    onBackClick: () -> Unit
) {
    val channel by viewModel.currentPlayingChannel.collectAsState()
    val channels by viewModel.selectedChannels.collectAsState()
    val hardwareAcceleration = viewModel.settingsManager.isHardwareAccelerationEnabled.value
    
    val context = LocalContext.current
    val exoPlayerManager = remember { ExoPlayerManager(context) }
    val player = remember { exoPlayerManager.initializePlayer(hardwareAcceleration) }
    var isPlaying by remember { mutableStateOf(false) }
    var showControls by remember { mutableStateOf(true) }
    var isLandscape by remember { mutableStateOf(false) }
    var showSidebar by remember { mutableStateOf(false) }
    var volume by remember { mutableStateOf(player.volume) }
    var showVolumeBar by remember { mutableStateOf(false) }
    
    var resizeMode by remember { mutableIntStateOf(AspectRatioFrameLayout.RESIZE_MODE_FIT) }
    
    val activity = remember(context) {
        var c = context
        while (c is ContextWrapper) {
            if (c is Activity) break
            c = c.baseContext
        }
        c as? Activity
    }

    DisposableEffect(player) {
        val listener = object : androidx.media3.common.Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) { isPlaying = playing }
        }
        player.addListener(listener)
        onDispose { player.removeListener(listener) }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayerManager.release()
            activity?.let { act ->
                act.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                val window = act.window
                val controller = WindowCompat.getInsetsController(window, window.decorView)
                controller.show(WindowInsetsCompat.Type.systemBars())
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
    }

    LaunchedEffect(isLandscape) {
        activity?.let { act ->
            val window = act.window
            val controller = WindowCompat.getInsetsController(window, window.decorView)
            if (isLandscape) {
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            } else {
                controller.show(WindowInsetsCompat.Type.systemBars())
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
    }

    LaunchedEffect(channel?.streamUrl) {
        channel?.streamUrl?.let { url -> exoPlayerManager.play(url) }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    this.player = player
                    this.useController = false 
                    this.resizeMode = resizeMode
                }
            },
            update = { view -> view.resizeMode = resizeMode },
            modifier = Modifier.fillMaxSize().clickable { 
                showControls = !showControls
                if (!showControls) {
                    showSidebar = false
                    showVolumeBar = false
                }
            }
        )

        // 사이드바 채널 목록 (투명 배경 및 여백 조절)
        AnimatedVisibility(
            visible = showSidebar,
            enter = slideInHorizontally(),
            exit = slideOutHorizontally()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(if (isLandscape) 300.dp else 260.dp)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                Column {
                    // 상단 토글 버튼들과 겹치지 않도록 충분한 상단 여백 확보
                    Spacer(modifier = Modifier.height(80.dp))
                    
                    Text(
                        text = stringResource(R.string.player_channel_list),
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        items(channels) { item ->
                            SidebarChannelItem(
                                channel = item,
                                isSelected = item.id == channel?.id,
                                onClick = { viewModel.selectChannel(item) }
                            )
                        }
                    }
                }
            }
        }

        if (showControls) {
            PlayerControlsOverlay(
                channel = channel,
                isPlaying = isPlaying,
                isLandscape = isLandscape,
                resizeMode = resizeMode,
                volume = volume,
                showVolumeBar = showVolumeBar,
                showSidebar = showSidebar,
                onPlayPauseToggle = { if (player.isPlaying) player.pause() else player.play() },
                onBackClick = onBackClick,
                onNextChannel = { viewModel.nextChannel() },
                onPrevChannel = { viewModel.prevChannel() },
                onFavoriteToggle = { channel?.let { viewModel.toggleFavorite(it) } },
                onToggleSidebar = { showSidebar = !showSidebar },
                onToggleVolume = { showVolumeBar = !showVolumeBar },
                onVolumeChange = { 
                    volume = it
                    player.volume = it
                },
                onToggleOrientation = {
                    isLandscape = !isLandscape
                    activity?.let { act ->
                        act.requestedOrientation = if (isLandscape) {
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        } else {
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        }
                    }
                },
                onToggleResizeMode = {
                    resizeMode = when (resizeMode) {
                        AspectRatioFrameLayout.RESIZE_MODE_FIT -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                        AspectRatioFrameLayout.RESIZE_MODE_ZOOM -> AspectRatioFrameLayout.RESIZE_MODE_FILL
                        else -> AspectRatioFrameLayout.RESIZE_MODE_FIT
                    }
                }
            )
        }
    }
}

@Composable
fun SidebarChannelItem(channel: Channel, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.Transparent)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = channel.thumbnail,
            contentDescription = null,
            modifier = Modifier.size(44.dp).clip(RoundedCornerShape(8.dp)).background(Color.Gray.copy(alpha = 0.3f)),
            contentScale = ContentScale.Fit,
            error = painterResource(id = android.R.drawable.ic_menu_gallery)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = channel.name,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.White,
            fontSize = 15.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(UnstableApi::class)
@Composable
fun PlayerControlsOverlay(
    channel: Channel?,
    isPlaying: Boolean,
    isLandscape: Boolean,
    resizeMode: Int,
    volume: Float,
    showVolumeBar: Boolean,
    showSidebar: Boolean,
    onPlayPauseToggle: () -> Unit,
    onBackClick: () -> Unit,
    onNextChannel: () -> Unit,
    onPrevChannel: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onToggleSidebar: () -> Unit,
    onToggleVolume: () -> Unit,
    onVolumeChange: (Float) -> Unit,
    onToggleOrientation: () -> Unit,
    onToggleResizeMode: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.7f),
                        Color.Transparent,
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.7f)
                    )
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .align(Alignment.TopCenter), 
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.player_back), tint = Color.White)
            }
            
            IconButton(onClick = onToggleSidebar) {
                Icon(
                    imageVector = if (showSidebar) Icons.AutoMirrored.Filled.MenuOpen else Icons.Default.Menu,
                    contentDescription = stringResource(R.string.player_sidebar),
                    tint = if (showSidebar) MaterialTheme.colorScheme.primary else Color.White
                )
            }

            IconButton(onClick = onFavoriteToggle) {
                Icon(
                    imageVector = if (channel?.isFavorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.player_favorites),
                    tint = if (channel?.isFavorite == true) Color.Red else Color.White
                )
            }

            Box(contentAlignment = Alignment.CenterStart) {
                IconButton(onClick = onToggleVolume) {
                    Icon(
                        imageVector = when {
                            volume == 0f -> Icons.AutoMirrored.Filled.VolumeOff
                            volume < 0.5f -> Icons.AutoMirrored.Filled.VolumeDown
                            else -> Icons.AutoMirrored.Filled.VolumeUp
                        },
                        contentDescription = stringResource(R.string.player_volume),
                        tint = Color.White
                    )
                }
                if (showVolumeBar) {
                    Card(
                        modifier = Modifier.padding(start = 48.dp).width(160.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.8f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Slider(
                            value = volume,
                            onValueChange = onVolumeChange,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            
            IconButton(onClick = onToggleResizeMode) {
                Icon(
                    imageVector = when (resizeMode) {
                        AspectRatioFrameLayout.RESIZE_MODE_ZOOM -> Icons.Default.ZoomIn
                        AspectRatioFrameLayout.RESIZE_MODE_FILL -> Icons.Default.AspectRatio
                        else -> Icons.Default.FitScreen
                    },
                    contentDescription = stringResource(R.string.player_aspect_ratio),
                    tint = Color.White
                )
            }

            IconButton(onClick = onToggleOrientation) {
                Icon(
                    imageVector = if (isLandscape) Icons.Default.FullscreenExit else Icons.Default.Fullscreen, 
                    contentDescription = stringResource(R.string.player_toggle_screen), 
                    tint = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = channel?.name ?: stringResource(R.string.player_no_info), 
                        color = Color.White, 
                        fontWeight = FontWeight.Bold, 
                        fontSize = 18.sp, 
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = channel?.category ?: "", 
                        color = Color.White.copy(alpha = 0.7f), 
                        fontSize = 13.sp
                    )
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onPrevChannel) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = stringResource(R.string.player_prev), tint = Color.White, modifier = Modifier.size(28.dp))
                    }
                    IconButton(onClick = onPlayPauseToggle) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = stringResource(R.string.player_play_pause),
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    IconButton(onClick = onNextChannel) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = stringResource(R.string.player_next), tint = Color.White, modifier = Modifier.size(28.dp))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.White.copy(alpha = 0.2f)
            )
        }
    }
}
