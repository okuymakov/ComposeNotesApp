package com.example.notes.data.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.*

class NoteRealm : RealmObject {
    @PrimaryKey
    var id: Long = UUID.randomUUID().mostSignificantBits
    var title: String = ""
    var createdAt: Long = Date().time
    var content: String = ""
    var color: Int = 0
    var tags: RealmList<TagRealm> = realmListOf()
}