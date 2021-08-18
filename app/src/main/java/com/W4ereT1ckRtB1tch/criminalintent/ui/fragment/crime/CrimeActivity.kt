package com.W4ereT1ckRtB1tch.criminalintent.ui.fragment.crime

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.W4ereT1ckRtB1tch.criminalintent.ui.fragment.SingleFragmentActivity
import java.util.*


class CrimeActivity : SingleFragmentActivity() {

    companion object {
        private const val EXTRA_CRIME_ID = "com.android.criminal_intent.crime_id"

        fun newIntent(packageContext: Context, crimeId: UUID): Intent {
            val intent = Intent(packageContext, CrimeActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }

    override fun createFragment(): Fragment {
        val crimeId: UUID = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        return CrimeFragment.newInstance(crimeId)
    }

}