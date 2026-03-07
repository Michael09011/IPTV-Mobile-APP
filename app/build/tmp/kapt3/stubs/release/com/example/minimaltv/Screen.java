package com.example.minimaltv;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b7\u0018\u00002\u00020\u0001:\u0006\u000f\u0010\u0011\u0012\u0013\u0014B\u001f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u0082\u0001\u0006\u0015\u0016\u0017\u0018\u0019\u001a\u00a8\u0006\u001b"}, d2 = {"Lcom/example/minimaltv/Screen;", "", "route", "", "titleResId", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "(Ljava/lang/String;ILandroidx/compose/ui/graphics/vector/ImageVector;)V", "getIcon", "()Landroidx/compose/ui/graphics/vector/ImageVector;", "getRoute", "()Ljava/lang/String;", "getTitleResId", "()I", "AddPlaylist", "ChannelList", "Favorites", "Player", "Playlist", "Settings", "Lcom/example/minimaltv/Screen$AddPlaylist;", "Lcom/example/minimaltv/Screen$ChannelList;", "Lcom/example/minimaltv/Screen$Favorites;", "Lcom/example/minimaltv/Screen$Player;", "Lcom/example/minimaltv/Screen$Playlist;", "Lcom/example/minimaltv/Screen$Settings;", "app_release"})
public abstract class Screen {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String route = null;
    private final int titleResId = 0;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.ui.graphics.vector.ImageVector icon = null;
    
    private Screen(java.lang.String route, int titleResId, androidx.compose.ui.graphics.vector.ImageVector icon) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    public final int getTitleResId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.ui.graphics.vector.ImageVector getIcon() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/minimaltv/Screen$AddPlaylist;", "Lcom/example/minimaltv/Screen;", "()V", "app_release"})
    public static final class AddPlaylist extends com.example.minimaltv.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.minimaltv.Screen.AddPlaylist INSTANCE = null;
        
        private AddPlaylist() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/minimaltv/Screen$ChannelList;", "Lcom/example/minimaltv/Screen;", "()V", "app_release"})
    public static final class ChannelList extends com.example.minimaltv.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.minimaltv.Screen.ChannelList INSTANCE = null;
        
        private ChannelList() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/minimaltv/Screen$Favorites;", "Lcom/example/minimaltv/Screen;", "()V", "app_release"})
    public static final class Favorites extends com.example.minimaltv.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.minimaltv.Screen.Favorites INSTANCE = null;
        
        private Favorites() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/minimaltv/Screen$Player;", "Lcom/example/minimaltv/Screen;", "()V", "app_release"})
    public static final class Player extends com.example.minimaltv.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.minimaltv.Screen.Player INSTANCE = null;
        
        private Player() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/minimaltv/Screen$Playlist;", "Lcom/example/minimaltv/Screen;", "()V", "app_release"})
    public static final class Playlist extends com.example.minimaltv.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.minimaltv.Screen.Playlist INSTANCE = null;
        
        private Playlist() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/minimaltv/Screen$Settings;", "Lcom/example/minimaltv/Screen;", "()V", "app_release"})
    public static final class Settings extends com.example.minimaltv.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.example.minimaltv.Screen.Settings INSTANCE = null;
        
        private Settings() {
        }
    }
}