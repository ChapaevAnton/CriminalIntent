package com.W4ereT1ckRtB1tch.criminalintent.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.W4ereT1ckRtB1tch.criminalintent.database.CrimeBaseHelper
import java.util.*
import kotlin.collections.ArrayList

class CrimeLab private constructor(context: Context) {

    private val crimes: MutableList<Crime>
    private val mContext:Context = context.applicationContext
    private val mDataBase:SQLiteDatabase = CrimeBaseHelper(mContext).writableDatabase

    init {
        crimes = ArrayList()
    }

    companion object {
        private var crimeLab: CrimeLab? = null
        operator fun get(context: Context): CrimeLab? {
            if (crimeLab == null) {
                crimeLab = CrimeLab(context)
            }
            return crimeLab
        }
    }

    fun addCrime(crime: Crime) {
        crimes.add(crime)
    }

    fun deleteCrime(id: UUID) {
        crimes.remove(getCrime(id))
    }

    fun getCrimes(): List<Crime> {
        return crimes
    }

    fun getCrime(id: UUID): Crime? {
        return crimes.find { it.id == id }
    }

}