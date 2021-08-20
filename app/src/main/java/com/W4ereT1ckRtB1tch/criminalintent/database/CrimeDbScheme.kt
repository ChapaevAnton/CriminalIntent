package com.W4ereT1ckRtB1tch.criminalintent.database

class CrimeDbScheme {

    class CrimeTable {
        companion object {
            const val NAME = "crimes"
        }

        class Cols {
            companion object {
                const val UUID = "uuid"
                const val TITLE = "title"
                const val DATE = "date"
                const val TIME = "time"
                const val SOLVED = "solved"
            }
        }
    }

}