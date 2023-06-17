package com.example.notes.domain.models

import java.util.*

data class Tag(val id: Long = UUID.randomUUID().mostSignificantBits, val name: String)

