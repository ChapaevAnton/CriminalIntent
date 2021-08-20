package com.W4ereT1ckRtB1tch.criminalintent.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.W4ereT1ckRtB1tch.criminalintent.database.CrimeBaseHelper
import com.W4ereT1ckRtB1tch.criminalintent.database.CrimeCursorWrapper
import com.W4ereT1ckRtB1tch.criminalintent.database.CrimeDbScheme.CrimeTable
import java.util.*

class CrimeLab private constructor(context: Context) {


    private val mContext: Context = context.applicationContext
    private val mDataBase: SQLiteDatabase = CrimeBaseHelper(mContext).writableDatabase

    init {

    }

    companion object {
        private var crimeLab: CrimeLab? = null
        operator fun get(context: Context): CrimeLab? {
            if (crimeLab == null) {
                crimeLab = CrimeLab(context)
            }
            return crimeLab
        }

        private fun getContentValues(crime: Crime): ContentValues {
            val values = ContentValues()
            values.put(CrimeTable.Cols.UUID, crime.id.toString())
            values.put(CrimeTable.Cols.TITLE, crime.title)
            values.put(CrimeTable.Cols.DATE, crime.date.time)
            values.put(CrimeTable.Cols.TIME, crime.time.time)
            values.put(CrimeTable.Cols.SOLVED, if (crime.solved) 1 else 0)
            return values
        }
    }

    fun addCrime(crime: Crime) {
        val values = getContentValues(crime)
        mDataBase.insert(CrimeTable.NAME, null, values)
    }

    fun updateCrime(crime: Crime) {
        val uuidString = crime.id.toString()
        val values = getContentValues(crime)
        val selection = "${CrimeTable.Cols.UUID} = ?"
        val selectionArgs = arrayOf(uuidString)
        mDataBase.update(CrimeTable.NAME, values, selection, selectionArgs)
    }

    fun deleteCrime(id: UUID) {
        //crimes.remove(getCrime(id))
    }

    fun getCrimes(): List<Crime> {
        val crimes: MutableList<Crime> = arrayListOf()
        val cursor = queryCrimes(null, null)
        cursor.use { cursor ->
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                crimes.add(cursor.getCrime())
                cursor.moveToNext()
            }
        }
        return crimes
    }

    fun getCrime(id: UUID): Crime? {
        val selection = "${CrimeTable.Cols.UUID} = ?"
        val selectionArgs = arrayOf(id.toString())
        val cursor = queryCrimes(selection, selectionArgs)
        cursor.use { cursor ->
            if (cursor.count == 0) return null
            cursor.moveToFirst()
            return cursor.getCrime()
        }
    }

    private fun queryCrimes(whereClause: String?, whereArgs: Array<String>?): CrimeCursorWrapper {
        val cursor = mDataBase.query(
            CrimeTable.NAME,
            null,
            whereClause,
            whereArgs,
            null,
            null,
            null
        )
        return CrimeCursorWrapper(cursor)
    }

}