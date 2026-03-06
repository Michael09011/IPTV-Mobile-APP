package com.example.minimaltv.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&J\u0016\u0010\'\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010(\u001a\u00020$J\u000e\u0010)\u001a\u00020\"2\u0006\u0010*\u001a\u00020\u000bJ\u000e\u0010+\u001a\u00020\"2\u0006\u0010,\u001a\u00020$J\b\u0010-\u001a\u00020\"H\u0002J\u0006\u0010.\u001a\u00020\"J\u0006\u0010/\u001a\u00020\"J\u0006\u00100\u001a\u00020\"J\u000e\u00101\u001a\u00020\"2\u0006\u0010*\u001a\u00020\u000bJ\u0016\u00102\u001a\u00020\"2\u0006\u0010*\u001a\u00020\u000b2\u0006\u00103\u001a\u00020$J\u000e\u00104\u001a\u00020\"2\u0006\u00105\u001a\u00020\u0007J\u0016\u00106\u001a\u00020\"2\u0006\u00107\u001a\u00020$H\u0082@\u00a2\u0006\u0002\u00108J\u000e\u00109\u001a\u00020\"2\u0006\u00105\u001a\u00020\u0007R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012R\u001d\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\t0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0012R\u0011\u0010\u001d\u001a\u00020\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 \u00a8\u0006:"}, d2 = {"Lcom/example/minimaltv/ui/TvViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "application", "Landroid/app/Application;", "(Landroid/app/Application;)V", "_currentPlayingChannel", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/example/minimaltv/data/model/Channel;", "_favorites", "", "_playlists", "Lcom/example/minimaltv/data/model/Playlist;", "_selectedChannels", "channelDao", "Lcom/example/minimaltv/data/local/ChannelDao;", "currentPlayingChannel", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentPlayingChannel", "()Lkotlinx/coroutines/flow/StateFlow;", "database", "Lcom/example/minimaltv/data/local/AppDatabase;", "favorites", "getFavorites", "playlistDao", "Lcom/example/minimaltv/data/local/PlaylistDao;", "playlists", "getPlaylists", "selectedChannels", "getSelectedChannels", "settingsManager", "Lcom/example/minimaltv/data/local/SettingsManager;", "getSettingsManager", "()Lcom/example/minimaltv/data/local/SettingsManager;", "addPlaylistFromLocalFile", "", "name", "", "uri", "Landroid/net/Uri;", "addPlaylistFromUrl", "url", "deletePlaylist", "playlist", "loadChannelsForPlaylist", "playlistId", "loadData", "nextChannel", "prevChannel", "refreshAllPlaylists", "refreshPlaylist", "renamePlaylist", "newName", "selectChannel", "channel", "showToast", "message", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toggleFavorite", "app_debug"})
public final class TvViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.example.minimaltv.data.local.AppDatabase database = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.minimaltv.data.local.PlaylistDao playlistDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.minimaltv.data.local.ChannelDao channelDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.minimaltv.data.local.SettingsManager settingsManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.minimaltv.data.model.Playlist>> _playlists = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.minimaltv.data.model.Playlist>> playlists = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.minimaltv.data.model.Channel>> _favorites = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.minimaltv.data.model.Channel>> favorites = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.example.minimaltv.data.model.Channel>> _selectedChannels = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.minimaltv.data.model.Channel>> selectedChannels = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.example.minimaltv.data.model.Channel> _currentPlayingChannel = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.example.minimaltv.data.model.Channel> currentPlayingChannel = null;
    
    public TvViewModel(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.minimaltv.data.local.SettingsManager getSettingsManager() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.minimaltv.data.model.Playlist>> getPlaylists() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.minimaltv.data.model.Channel>> getFavorites() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.example.minimaltv.data.model.Channel>> getSelectedChannels() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.example.minimaltv.data.model.Channel> getCurrentPlayingChannel() {
        return null;
    }
    
    private final void loadData() {
    }
    
    public final void loadChannelsForPlaylist(@org.jetbrains.annotations.NotNull()
    java.lang.String playlistId) {
    }
    
    public final void selectChannel(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.model.Channel channel) {
    }
    
    public final void nextChannel() {
    }
    
    public final void prevChannel() {
    }
    
    public final void addPlaylistFromUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    public final void addPlaylistFromLocalFile(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    public final void renamePlaylist(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.model.Playlist playlist, @org.jetbrains.annotations.NotNull()
    java.lang.String newName) {
    }
    
    public final void toggleFavorite(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.model.Channel channel) {
    }
    
    public final void deletePlaylist(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.model.Playlist playlist) {
    }
    
    public final void refreshPlaylist(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.model.Playlist playlist) {
    }
    
    public final void refreshAllPlaylists() {
    }
    
    private final java.lang.Object showToast(java.lang.String message, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}