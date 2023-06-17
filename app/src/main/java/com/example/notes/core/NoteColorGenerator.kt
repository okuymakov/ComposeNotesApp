package com.example.notes.core

import androidx.compose.ui.graphics.toArgb
import com.example.notes.ui.theme.*
import kotlin.random.Random


private val colors = arrayListOf(RedOrange, Champagne, Blue, Green, Yellow)

fun generateNoteColor(): Int {
    return colors[Random.nextInt(colors.size) % colors.size].toArgb()
}
