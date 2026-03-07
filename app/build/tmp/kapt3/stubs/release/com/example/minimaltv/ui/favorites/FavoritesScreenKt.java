package com.example.minimaltv.ui.favorites;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\u001a,\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0007\u001a>\u0010\u0007\u001a\u00020\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000b2\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u00a8\u0006\f"}, d2 = {"FavoriteChannelCard", "", "channel", "Lcom/example/minimaltv/data/model/Channel;", "onClick", "Lkotlin/Function0;", "onFavoriteToggle", "FavoritesScreen", "favoriteChannels", "", "onChannelClick", "Lkotlin/Function1;", "app_release"})
public final class FavoritesScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void FavoritesScreen(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.minimaltv.data.model.Channel> favoriteChannels, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.minimaltv.data.model.Channel, kotlin.Unit> onChannelClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.example.minimaltv.data.model.Channel, kotlin.Unit> onFavoriteToggle) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FavoriteChannelCard(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.model.Channel channel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onFavoriteToggle) {
    }
}