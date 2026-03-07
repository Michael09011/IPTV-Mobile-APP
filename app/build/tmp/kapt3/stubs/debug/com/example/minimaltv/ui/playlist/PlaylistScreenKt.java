package com.example.minimaltv.ui.playlist;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00008\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u001aH\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u0096\u0001\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u000e2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000e2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000e2\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00010\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a\u001e\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u00102\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u00a8\u0006\u0019"}, d2 = {"PlaylistCard", "", "playlist", "Lcom/example/minimaltv/data/model/Playlist;", "onClick", "Lkotlin/Function0;", "onDelete", "onRefresh", "onRename", "PlaylistScreen", "viewModel", "Lcom/example/minimaltv/ui/TvViewModel;", "onAddClick", "onPlaylistClick", "Lkotlin/Function1;", "onChannelClick", "Lcom/example/minimaltv/data/model/Channel;", "onDeletePlaylist", "onRefreshPlaylist", "onRenamePlaylist", "Lkotlin/Function2;", "", "onRefreshAll", "RecentChannelItem", "channel", "app_debug"})
public final class PlaylistScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void PlaylistScreen(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.ui.TvViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.minimaltv.data.model.Playlist, kotlin.Unit> onPlaylistClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.minimaltv.data.model.Channel, kotlin.Unit> onChannelClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.minimaltv.data.model.Playlist, kotlin.Unit> onDeletePlaylist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.minimaltv.data.model.Playlist, kotlin.Unit> onRefreshPlaylist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.example.minimaltv.data.model.Playlist, ? super java.lang.String, kotlin.Unit> onRenamePlaylist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefreshAll) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RecentChannelItem(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.model.Channel channel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PlaylistCard(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.model.Playlist playlist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRename) {
    }
}