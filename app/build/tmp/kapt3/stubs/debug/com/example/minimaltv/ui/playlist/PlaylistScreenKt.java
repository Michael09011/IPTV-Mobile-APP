package com.example.minimaltv.ui.playlist;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u001at\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a\u00bc\u0001\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00132\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00152\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00132\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00132\u001e\u0010\u0019\u001a\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00010\u001a2\u0018\u0010\u001c\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00010\u00152\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u001a\u001e\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u00162\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0007\u00a8\u0006 "}, d2 = {"PlaylistCard", "", "playlist", "Lcom/example/minimaltv/data/model/Playlist;", "isFirst", "", "isLast", "onClick", "Lkotlin/Function0;", "onDelete", "onRefresh", "onEdit", "onMoveUp", "onMoveDown", "PlaylistScreen", "viewModel", "Lcom/example/minimaltv/ui/TvViewModel;", "onAddClick", "onPlaylistClick", "Lkotlin/Function1;", "onChannelClick", "Lkotlin/Function2;", "Lcom/example/minimaltv/data/model/Channel;", "onDeletePlaylist", "onRefreshPlaylist", "onEditPlaylist", "Lkotlin/Function3;", "", "onMovePlaylist", "onRefreshAll", "RecentChannelItem", "channel", "app_debug"})
public final class PlaylistScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void PlaylistScreen(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.ui.TvViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.minimaltv.data.model.Playlist, kotlin.Unit> onPlaylistClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.example.minimaltv.data.model.Channel, ? super java.lang.Boolean, kotlin.Unit> onChannelClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.minimaltv.data.model.Playlist, kotlin.Unit> onDeletePlaylist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.minimaltv.data.model.Playlist, kotlin.Unit> onRefreshPlaylist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function3<? super com.example.minimaltv.data.model.Playlist, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onEditPlaylist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super com.example.minimaltv.data.model.Playlist, ? super java.lang.Boolean, kotlin.Unit> onMovePlaylist, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefreshAll) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PlaylistCard(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.model.Playlist playlist, boolean isFirst, boolean isLast, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onEdit, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMoveUp, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onMoveDown) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RecentChannelItem(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.model.Channel channel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}