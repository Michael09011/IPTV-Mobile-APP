package com.example.minimaltv.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\rJ\u000e\u0010\n\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u0007J\u000e\u0010\"\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\rJ\u000e\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u0007J\u000e\u0010\u001a\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\u0018J\u000e\u0010\u001e\u001a\u00020\u001f2\u0006\u0010&\u001a\u00020\u001cR \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR \u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\t\"\u0004\b\u000e\u0010\u000bR \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\t\"\u0004\b\u0010\u0010\u000bR\u0016\u0010\u0011\u001a\n \u0013*\u0004\u0018\u00010\u00120\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\t\"\u0004\b\u0016\u0010\u000bR \u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\t\"\u0004\b\u001a\u0010\u000bR \u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\t\"\u0004\b\u001e\u0010\u000b\u00a8\u0006\'"}, d2 = {"Lcom/example/minimaltv/data/local/SettingsManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "epgUrl", "Landroidx/compose/runtime/MutableState;", "", "getEpgUrl", "()Landroidx/compose/runtime/MutableState;", "setEpgUrl", "(Landroidx/compose/runtime/MutableState;)V", "isEpgEnabled", "", "setEpgEnabled", "isHardwareAccelerationEnabled", "setHardwareAccelerationEnabled", "prefs", "Landroid/content/SharedPreferences;", "kotlin.jvm.PlatformType", "selectedLanguage", "getSelectedLanguage", "setSelectedLanguage", "themeMode", "Lcom/example/minimaltv/data/local/ThemeMode;", "getThemeMode", "setThemeMode", "updateInterval", "Lcom/example/minimaltv/data/local/UpdateInterval;", "getUpdateInterval", "setUpdateInterval", "", "enabled", "url", "setHardwareAcceleration", "setLanguage", "languageCode", "mode", "interval", "app_debug"})
public final class SettingsManager {
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private androidx.compose.runtime.MutableState<java.lang.Boolean> isHardwareAccelerationEnabled;
    @org.jetbrains.annotations.NotNull()
    private androidx.compose.runtime.MutableState<java.lang.Boolean> isEpgEnabled;
    @org.jetbrains.annotations.NotNull()
    private androidx.compose.runtime.MutableState<java.lang.String> epgUrl;
    @org.jetbrains.annotations.NotNull()
    private androidx.compose.runtime.MutableState<java.lang.String> selectedLanguage;
    @org.jetbrains.annotations.NotNull()
    private androidx.compose.runtime.MutableState<com.example.minimaltv.data.local.ThemeMode> themeMode;
    @org.jetbrains.annotations.NotNull()
    private androidx.compose.runtime.MutableState<com.example.minimaltv.data.local.UpdateInterval> updateInterval;
    
    public SettingsManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.MutableState<java.lang.Boolean> isHardwareAccelerationEnabled() {
        return null;
    }
    
    public final void setHardwareAccelerationEnabled(@org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.MutableState<java.lang.Boolean> isEpgEnabled() {
        return null;
    }
    
    public final void setEpgEnabled(@org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<java.lang.Boolean> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.MutableState<java.lang.String> getEpgUrl() {
        return null;
    }
    
    public final void setEpgUrl(@org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.MutableState<java.lang.String> getSelectedLanguage() {
        return null;
    }
    
    public final void setSelectedLanguage(@org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<java.lang.String> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.MutableState<com.example.minimaltv.data.local.ThemeMode> getThemeMode() {
        return null;
    }
    
    public final void setThemeMode(@org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<com.example.minimaltv.data.local.ThemeMode> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.compose.runtime.MutableState<com.example.minimaltv.data.local.UpdateInterval> getUpdateInterval() {
        return null;
    }
    
    public final void setUpdateInterval(@org.jetbrains.annotations.NotNull()
    androidx.compose.runtime.MutableState<com.example.minimaltv.data.local.UpdateInterval> p0) {
    }
    
    public final void setHardwareAcceleration(boolean enabled) {
    }
    
    public final void setEpgEnabled(boolean enabled) {
    }
    
    public final void setEpgUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    public final void setLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String languageCode) {
    }
    
    public final void setThemeMode(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.local.ThemeMode mode) {
    }
    
    public final void setUpdateInterval(@org.jetbrains.annotations.NotNull()
    com.example.minimaltv.data.local.UpdateInterval interval) {
    }
}