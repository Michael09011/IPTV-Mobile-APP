package com.example.minimaltv.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\'J\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0011\u001a\u00020\u00032\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00a7@\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006\u0014"}, d2 = {"Lcom/example/minimaltv/data/local/PlaylistDao;", "", "deletePlaylistById", "", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPlaylists", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/minimaltv/data/model/Playlist;", "getMaxOrder", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertPlaylist", "playlist", "(Lcom/example/minimaltv/data/model/Playlist;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePlaylists", "playlists", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface PlaylistDao {
    
    @androidx.room.Query(value = "SELECT * FROM playlists ORDER BY displayOrder ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.minimaltv.data.model.Playlist>> getAllPlaylists();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPlaylist(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.model.Playlist playlist, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePlaylists(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.minimaltv.data.model.Playlist> playlists, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM playlists WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deletePlaylistById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT MAX(displayOrder) FROM playlists")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMaxOrder(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}