package com.example.minimaltv.data.local

import android.content.Context
import androidx.room.*
import com.example.minimaltv.data.model.Channel
import com.example.minimaltv.data.model.Playlist
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistDao {
    @Query("SELECT * FROM playlists")
    fun getAllPlaylists(): Flow<List<Playlist>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: Playlist)

    @Query("DELETE FROM playlists WHERE id = :id")
    suspend fun deletePlaylistById(id: String)
}

@Dao
interface ChannelDao {
    @Query("SELECT * FROM channels WHERE playlistId = :playlistId")
    fun getChannelsByPlaylist(playlistId: String): Flow<List<Channel>>

    @Query("SELECT * FROM channels WHERE isFavorite = 1")
    fun getFavoriteChannels(): Flow<List<Channel>>

    @Query("SELECT * FROM channels WHERE lastWatched > 0 ORDER BY lastWatched DESC LIMIT 5")
    fun getRecentChannels(): Flow<List<Channel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannels(channels: List<Channel>)

    @Update
    suspend fun updateChannel(channel: Channel)
    
    @Query("DELETE FROM channels WHERE playlistId = :playlistId")
    suspend fun deleteChannelsByPlaylist(playlistId: String)
}

@Database(entities = [Playlist::class, Channel::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
    abstract fun channelDao(): ChannelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "minimal_tv_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
