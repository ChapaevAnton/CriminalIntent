package com.W4ereT1ckRtB1tch.criminalintent.database

import android.database.Cursor
import android.database.CursorWrapper
import com.W4ereT1ckRtB1tch.criminalintent.data.Crime
import com.W4ereT1ckRtB1tch.criminalintent.database.CrimeDbScheme.CrimeTable
import java.util.*

class CrimeCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {

    fun getCrime(): Crime {
        val uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID))
        val title = getString(getColumnIndex(CrimeTable.Cols.TITLE))
        val date = getLong(getColumnIndex(CrimeTable.Cols.DATE))
        val time = getLong(getColumnIndex(CrimeTable.Cols.TIME))
        val solved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED))

        val crime = Crime(UUID.fromString(uuidString))
        crime.title = title
        crime.date = Date(date)
        crime.time = Date(time)
        crime.solved = solved != 0

        return crime
    }
}