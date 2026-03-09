package com.example.minimaltv.data.model

data class EpgProgram(
    val channelId: String,
    val title: String,
    val startTime: Long,
    val endTime: Long,
    val description: String = ""
)
