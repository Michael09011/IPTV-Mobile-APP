package com.example.minimaltv.data.parser

import android.util.Xml
import com.example.minimaltv.data.model.EpgProgram
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

object EpgParser {
    private val dateFormat = SimpleDateFormat("yyyyMMddHHmmss Z", Locale.US)

    fun parse(inputStream: InputStream): List<EpgProgram> {
        val programs = mutableListOf<EpgProgram>()
        val parser = Xml.newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(inputStream, null)

        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.name == "programme") {
                programs.add(readProgramme(parser))
            }
            eventType = parser.next()
        }
        return programs
    }

    private fun readProgramme(parser: XmlPullParser): EpgProgram {
        val channelId = parser.getAttributeValue(null, "channel")
        val startStr = parser.getAttributeValue(null, "start")
        val endStr = parser.getAttributeValue(null, "stop")
        
        var title = ""
        var desc = ""

        while (!(parser.next() == XmlPullParser.END_TAG && parser.name == "programme")) {
            if (parser.eventType != XmlPullParser.START_TAG) continue
            when (parser.name) {
                "title" -> title = readText(parser)
                "desc" -> desc = readText(parser)
                else -> skip(parser)
            }
        }

        return EpgProgram(
            channelId = channelId,
            title = title,
            startTime = parseDate(startStr),
            endTime = parseDate(endStr),
            description = desc
        )
    }

    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    private fun parseDate(dateStr: String?): Long {
        if (dateStr == null) return 0
        return try {
            dateFormat.parse(dateStr)?.time ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) throw IllegalStateException()
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}
