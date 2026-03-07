package com.example.minimaltv.ui.player;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\u001a\u00d4\u0001\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u00052\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a&\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u00052\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001a\u001e\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u00a8\u0006\u001f"}, d2 = {"PlayerControlsOverlay", "", "channel", "Lcom/example/minimaltv/data/model/Channel;", "isPlaying", "", "isLandscape", "resizeMode", "", "volume", "", "showVolumeBar", "showSidebar", "onPlayPauseToggle", "Lkotlin/Function0;", "onBackClick", "onNextChannel", "onPrevChannel", "onFavoriteToggle", "onToggleSidebar", "onToggleVolume", "onVolumeChange", "Lkotlin/Function1;", "onToggleOrientation", "onToggleResizeMode", "SidebarChannelItem", "isSelected", "onClick", "VideoPlayerScreen", "viewModel", "Lcom/example/minimaltv/ui/TvViewModel;", "app_debug"})
public final class VideoPlayerScreenKt {
    
    @androidx.annotation.OptIn(markerClass = {androidx.media3.common.util.UnstableApi.class})
    @androidx.compose.runtime.Composable()
    public static final void VideoPlayerScreen(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.ui.TvViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SidebarChannelItem(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.model.Channel channel, boolean isSelected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.annotation.OptIn(markerClass = {androidx.media3.common.util.UnstableApi.class})
    @androidx.compose.runtime.Composable()
    public static final void PlayerControlsOverlay(@org.jetbrains.annotations.Nullable()
    com.example.minimaltv.data.model.Channel channel, boolean isPlaying, boolean isLandscape, int resizeMode, float volume, boolean showVolumeBar, boolean showSidebar, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPlayPauseToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNextChannel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPrevChannel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFavoriteToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleSidebar, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleVolume, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Float, kotlin.Unit> onVolumeChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleOrientation, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleResizeMode) {
    }
}