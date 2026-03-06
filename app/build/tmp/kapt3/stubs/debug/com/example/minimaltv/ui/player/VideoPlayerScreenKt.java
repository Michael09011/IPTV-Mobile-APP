package com.example.minimaltv.ui.player;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a~\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001aH\u0010\u0010\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0011\u001a\u00020\u00052\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a\f\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u00020\u0014\u00a8\u0006\u0015"}, d2 = {"PlayerControlsOverlay", "", "channel", "Lcom/example/minimaltv/data/model/Channel;", "isPlaying", "", "isLandscape", "resizeMode", "", "onPlayPauseToggle", "Lkotlin/Function0;", "onBackClick", "onNextChannel", "onPrevChannel", "onToggleOrientation", "onToggleResizeMode", "VideoPlayerScreen", "hardwareAcceleration", "findActivity", "Landroid/app/Activity;", "Landroid/content/Context;", "app_debug"})
public final class VideoPlayerScreenKt {
    
    @androidx.annotation.OptIn(markerClass = {androidx.media3.common.util.UnstableApi.class})
    @androidx.compose.runtime.Composable()
    public static final void VideoPlayerScreen(@org.jetbrains.annotations.Nullable()
    com.example.minimaltv.data.model.Channel channel, boolean hardwareAcceleration, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNextChannel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPrevChannel) {
    }
    
    @androidx.annotation.OptIn(markerClass = {androidx.media3.common.util.UnstableApi.class})
    @androidx.compose.runtime.Composable()
    public static final void PlayerControlsOverlay(@org.jetbrains.annotations.Nullable()
    com.example.minimaltv.data.model.Channel channel, boolean isPlaying, boolean isLandscape, int resizeMode, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPlayPauseToggle, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBackClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNextChannel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onPrevChannel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleOrientation, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleResizeMode) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final android.app.Activity findActivity(@org.jetbrains.annotations.NotNull()
    android.content.Context $this$findActivity) {
        return null;
    }
}