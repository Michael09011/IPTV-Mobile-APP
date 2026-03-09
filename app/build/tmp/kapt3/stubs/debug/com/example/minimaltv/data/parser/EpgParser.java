package com.example.minimaltv.data.parser;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tJ\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0002J\u0010\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/example/minimaltv/data/parser/EpgParser;", "", "()V", "dateFormat", "Ljava/text/SimpleDateFormat;", "parse", "", "Lcom/example/minimaltv/data/model/EpgProgram;", "inputStream", "Ljava/io/InputStream;", "parseDate", "", "dateStr", "", "readProgramme", "parser", "Lorg/xmlpull/v1/XmlPullParser;", "readText", "skip", "", "app_debug"})
public final class EpgParser {
    @org.jetbrains.annotations.NotNull()
    private static final java.text.SimpleDateFormat dateFormat = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.minimaltv.data.parser.EpgParser INSTANCE = null;
    
    private EpgParser() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.minimaltv.data.model.EpgProgram> parse(@org.jetbrains.annotations.NotNull()
    java.io.InputStream inputStream) {
        return null;
    }
    
    private final com.example.minimaltv.data.model.EpgProgram readProgramme(org.xmlpull.v1.XmlPullParser parser) {
        return null;
    }
    
    private final java.lang.String readText(org.xmlpull.v1.XmlPullParser parser) {
        return null;
    }
    
    private final long parseDate(java.lang.String dateStr) {
        return 0L;
    }
    
    private final void skip(org.xmlpull.v1.XmlPullParser parser) {
    }
}