package com.W4ereT1ckRtB1tch.criminalintent.ui.crime

import androidx.fragment.app.Fragment
import com.W4ereT1ckRtB1tch.criminalintent.SingleFragmentActivity

class CrimeActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return CrimeFragment()
    }

}