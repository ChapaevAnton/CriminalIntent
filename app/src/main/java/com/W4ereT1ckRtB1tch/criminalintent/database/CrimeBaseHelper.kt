package com.W4ereT1ckRtB1tch.criminalintent.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.W4ereT1ckRtB1tch.criminalintent.database.CrimeDbScheme.CrimeTable

class CrimeBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    companion object {
        const val VERSION = 1
        const val DATABASE_NAME = "crimeBase.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """CREATE TABLE ${CrimeTable.NAME} (
                |_id INTEGER PRIMARY KEY AUTOINCREMENT, 
                |${CrimeTable.Cols.UUID},
                |${CrimeTable.Cols.TITLE},
                |${CrimeTable.Cols.DATE},
                |${CrimeTable.Cols.TIME},
                |${CrimeTable.Cols.SOLVED})""".trimMargin()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


}