package com.example.minimaltv.data.parser

import com.example.minimaltv.data.model.Channel
import java.util.UUID

object M3uParser {
    fun parse(content: String, playlistId: String): List<Channel> {
        val channels = mutableListOf<Channel>()
        val lines = content.lines()
        var currentName = ""
        var currentLogo = ""
        var currentCategory = "기타"

        lines.forEach { line ->
            when {
                line.startsWith("#EXTINF:") -> {
                    currentName = line.substringAfterLast(",").trim()
                    currentLogo = extractAttribute(line, "tvg-logo")
                    currentCategory = extractAttribute(line, "group-title").ifEmpty { "기타" }
                }
                line.startsWith("http") -> {
                    if (currentName.isNotEmpty()) {
                        val streamUrl = line.trim()
                        // streamUrl과 playlistId를 조합하여 고유한 ID 생성 (즐겨찾기 유지용)
                        val uniqueId = UUID.nameUUIDFromBytes("${playlistId}_$streamUrl".toByteArray()).toString()
                        
                        channels.add(
                            Channel(
                                id = uniqueId,
                                playlistId = playlistId,
                                name = currentName,
                                thumbnail = currentLogo,
                                category = currentCategory,
                                streamUrl = streamUrl
                            )
                        )
                    }
                }
            }
        }
        return channels
    }

    private fun extractAttribute(line: String, attribute: String): String {
        val key = "$attribute=\""
        if (!line.contains(key)) return ""
        val start = line.indexOf(key) + key.length
        val end = line.indexOf("\"", start)
        return if (end != -1) line.substring(start, end) else ""
    }
}
