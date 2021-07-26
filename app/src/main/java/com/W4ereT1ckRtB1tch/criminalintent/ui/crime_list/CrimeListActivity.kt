package com.W4ereT1ckRtB1tch.criminalintent.ui.crime_list

import androidx.fragment.app.Fragment
import com.W4ereT1ckRtB1tch.criminalintent.SingleFragmentActivity

class CrimeListActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return CrimeListFragment()
    }
}