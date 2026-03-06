package com.example.minimaltv.ui.player

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.view.WindowManager
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.minimaltv.R
import com.example.minimaltv.data.model.Channel
import com.example.minimaltv.player.ExoPlayerManager

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(
    channel: Channel?,
    hardwareAcceleration: Boolean,
    onBackClick: () -> Unit,
    onNextChannel: () -> Unit = {},
    onPrevChannel: () -> Unit = {}
) {
    val context = LocalContext.current
    val exoPlayerManager = remember { ExoPlayerManager(context) }
    val player = remember { exoPlayerManager.initializePlayer(hardwareAcceleration) }
    var isPlaying by remember { mutableStateOf(false) }
    var showControls by remember { mutableStateOf(true) }
    var isLandscape by remember { mutableStateOf(false) }
    
    // 화면 비율 상태 (0: FIT, 3: FILL, 4: ZOOM)
    var resizeMode by remember { mutableIntStateOf(AspectRatioFrameLayout.RESIZE_MODE_FIT) }

    val activity = remember(context) { context.findActivity() }

    DisposableEffect(player) {
        val listener = object : androidx.media3.common.Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
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
        channel?.streamUrl?.let { url ->
            exoPlayerManager.play(url)
        }
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
            update = { view ->
                view.resizeMode = resizeMode
            },
            modifier = Modifier.fillMaxSize().clickable { showControls = !showControls }
        )

        if (showControls) {
            PlayerControlsOverlay(
                channel = channel,
                isPlaying = isPlaying,
                isLandscape = isLandscape,
                resizeMode = resizeMode,
                onPlayPauseToggle = {
                    if (player.isPlaying) player.pause() else player.play()
                },
                onBackClick = onBackClick,
                onNextChannel = onNextChannel,
                onPrevChannel = onPrevChannel,
                onToggleOrientation = {
                    isLandscape = !isLandscape
                    activity?.requestedOrientation = if (isLandscape) {
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    } else {
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
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

@OptIn(UnstableApi::class)
@Composable
fun PlayerControlsOverlay(
    channel: Channel?,
    isPlaying: Boolean,
    isLandscape: Boolean,
    resizeMode: Int,
    onPlayPauseToggle: () -> Unit,
    onBackClick: () -> Unit,
    onNextChannel: () -> Unit,
    onPrevChannel: () -> Unit,
    onToggleOrientation: () -> Unit,
    onToggleResizeMode: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(alpha = 0.6f),
                        Color.Transparent,
                        Color.Transparent,
                        Color.Black.copy(alpha = 0.6f)
                    )
                )
            )
    ) {
        // 상단 바 (상태바 영역 패딩 적용)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.TopCenter), 
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.player_back), tint = Color.White)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = channel?.name ?: stringResource(R.string.player_no_info), 
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                if (channel?.currentProgram != null) {
                    Text(
                        text = stringResource(R.string.player_now_playing, channel.currentProgram),
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
            }
            
            // 화면 비율 변경 버튼
            IconButton(onClick = onToggleResizeMode) {
                Icon(
                    imageVector = when (resizeMode) {
                        AspectRatioFrameLayout.RESIZE_MODE_ZOOM -> Icons.Default.ZoomIn
                        AspectRatioFrameLayout.RESIZE_MODE_FILL -> Icons.Default.AspectRatio
                        else -> Icons.Default.FitScreen
                    },
                    contentDescription = "화면 비율",
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

        // 하단 컨트롤 바 (네비게이션 바 영역 패딩 적용)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black.copy(alpha = 0.4f))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = channel?.name ?: stringResource(R.string.player_no_info), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = channel?.category ?: "", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onPrevChannel) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = stringResource(R.string.player_prev), tint = Color.White)
                    }
                    IconButton(onClick = onPlayPauseToggle) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = stringResource(R.string.player_play_pause),
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    IconButton(onClick = onNextChannel) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = stringResource(R.string.player_next), tint = Color.White)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().height(4.dp).clip(CircleShape),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.White.copy(alpha = 0.3f)
            )
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
