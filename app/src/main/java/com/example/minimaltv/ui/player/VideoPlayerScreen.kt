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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
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

    val activity = remember(context) { context.findActivity() }

    // 재생 상태 모니터링
    DisposableEffect(player) {
        val listener = object : androidx.media3.common.Player.Listener {
            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
        }
        player.addListener(listener)
        onDispose { player.removeListener(listener) }
    }

    // 화면 나갈 때: 플레이어 리소스 해제 + 화면 방향 원복 + 시스템 바 다시 표시
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

    // 가로 모드일 때 시스템 바 숨기기 로직
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
                    useController = false 
                }
            },
            modifier = Modifier.fillMaxSize().clickable { showControls = !showControls }
        )

        if (showControls) {
            PlayerControlsOverlay(
                channel = channel,
                isPlaying = isPlaying,
                isLandscape = isLandscape,
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
                }
            )
        }
    }
}

@Composable
fun PlayerControlsOverlay(
    channel: Channel?,
    isPlaying: Boolean,
    isLandscape: Boolean,
    onPlayPauseToggle: () -> Unit,
    onBackClick: () -> Unit,
    onNextChannel: () -> Unit,
    onPrevChannel: () -> Unit,
    onToggleOrientation: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(if (isLandscape) 24.dp else 16.dp)
            .background(Color.Black.copy(alpha = 0.4f))
    ) {
        // 상단 바
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "뒤로", tint = Color.White)
            }
            Text(text = channel?.name ?: "실시간 스트리밍", color = Color.White, modifier = Modifier.weight(1f))
            IconButton(onClick = onToggleOrientation) {
                Icon(
                    imageVector = if (isLandscape) Icons.Default.FullscreenExit else Icons.Default.Fullscreen, 
                    contentDescription = "화면 전환", 
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 하단 컨트롤 바
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = channel?.name ?: "채널 정보 없음", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(text = channel?.category ?: "", color = Color.White.copy(alpha = 0.7f), fontSize = 12.sp)
                }
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onPrevChannel) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "이전", tint = Color.White)
                    }
                    IconButton(onClick = onPlayPauseToggle) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "재생/일시정지",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    IconButton(onClick = onNextChannel) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "다음", tint = Color.White)
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
