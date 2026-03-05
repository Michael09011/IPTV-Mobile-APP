package com.example.minimaltv.player

import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.datasource.DefaultDataSource

class ExoPlayerManager(private val context: Context) {
    private var player: ExoPlayer? = null

    fun initializePlayer(hardwareAcceleration: Boolean = true): ExoPlayer {
        val renderersFactory = DefaultRenderersFactory(context)
            .setExtensionRendererMode(
                if (hardwareAcceleration) DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER 
                else DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF
            )

        return ExoPlayer.Builder(context, renderersFactory)
            .build()
            .also { player = it }
    }

    fun play(url: String) {
        val uri = Uri.parse(url)
        val mediaItem = MediaItem.Builder()
            .setUri(uri)
            .setMimeType(if (url.contains(".m3u8")) MimeTypes.APPLICATION_M3U8 else null)
            .build()

        // HLS(.m3u8)를 위한 전용 소스 빌더 사용 (안정성 강화)
        val mediaSource = if (url.contains(".m3u8")) {
            HlsMediaSource.Factory(DefaultDataSource.Factory(context))
                .createMediaSource(mediaItem)
        } else {
            null
        }

        player?.let {
            if (mediaSource != null) it.setMediaSource(mediaSource)
            else it.setMediaItem(mediaItem)
            
            it.prepare()
            it.play()
        }
    }

    fun release() {
        player?.stop()
        player?.release()
        player = null
    }
}
