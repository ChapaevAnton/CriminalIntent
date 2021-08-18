package com.W4ereT1ckRtB1tch.criminalintent.ui.fragment.crime_list

import androidx.fragment.app.Fragment
import com.W4ereT1ckRtB1tch.criminalintent.ui.fragment.SingleFragmentActivity

class CrimeListActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return CrimeListFragment()
    }
}