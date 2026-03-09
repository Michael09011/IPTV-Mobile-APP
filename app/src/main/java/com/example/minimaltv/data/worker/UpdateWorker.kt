package com.example.minimaltv.data.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.minimaltv.R
import com.example.minimaltv.data.local.AppDatabase
import com.example.minimaltv.data.parser.M3uParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.net.URL

class UpdateWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val database = AppDatabase.getDatabase(applicationContext)
        val playlistDao = database.playlistDao()
        val channelDao = database.channelDao()

        try {
            val playlists = playlistDao.getAllPlaylists().first()
            var updateCount = 0

            playlists.forEach { playlist ->
                try {
                    val content = URL(playlist.url).readText()
                    val channels = M3uParser.parse(content, playlist.id)
                    
                    channelDao.deleteChannelsByPlaylist(playlist.id)
                    channelDao.insertChannels(channels)
                    playlistDao.insertPlaylist(playlist.copy(channelCount = channels.size))
                    updateCount++
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            if (updateCount > 0) {
                showNotification("업데이트 완료", "${updateCount}개의 플레이리스트가 최신 상태로 갱신되었습니다.")
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "update_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "App Updates", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.stat_notify_sync)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1001, notification)
    }
}
