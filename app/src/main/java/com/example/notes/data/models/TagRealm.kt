package com.example.notes.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.*

class TagRealm : RealmObject {
    @PrimaryKey
    var id: Long = UUID.randomUUID().mostSignificantBits
    var name: String = ""
}