package com.W4ereT1ckRtB1tch.criminalintent.data

import java.util.*

class Crime(uuid: UUID) {

    val id: UUID = uuid
    var title: String? = null
    var date: Date = Date()
    var time: Date = Date()
    var solved: Boolean = false

    constructor() : this(UUID.randomUUID())

}