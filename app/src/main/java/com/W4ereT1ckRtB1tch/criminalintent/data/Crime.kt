package com.W4ereT1ckRtB1tch.criminalintent.data

import java.util.*

class Crime() {

    val id: UUID = UUID.randomUUID()
    var title: String? = null
    var date: Date = Date()
    var time: Date = Date()
    var solved: Boolean = false


}