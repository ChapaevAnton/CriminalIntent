package com.W4ereT1ckRtB1tch.criminalintent.ui.crime

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.W4ereT1ckRtB1tch.criminalintent.R
import com.W4ereT1ckRtB1tch.criminalintent.data.Crime
import com.W4ereT1ckRtB1tch.criminalintent.data.CrimeLab
import java.util.*

class CrimePagerActivity : AppCompatActivity() {

    private lateinit var crimes: List<Crime>
    private lateinit var viewPager: ViewPager

    companion object {
        private const val EXTRA_CRIME_ID = "com.android.criminal_intent.crime_id"

        fun newIntent(packageContext: Context, crimeId: UUID): Intent {
            val intent = Intent(packageContext, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)

        val crimeId: UUID = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID

        CrimeLab[this]?.getCrimes().let {
            if (it != null) {
                crimes = it
            }
        }

        viewPager = findViewById(R.id.crime_view_pager)


        val fragmentManager = supportFragmentManager

        //FIXME FragmentStatePagerAdapter deprecated
        viewPager.adapter = object : FragmentStatePagerAdapter(fragmentManager) {
            override fun getCount(): Int {
                return crimes.size
            }

            override fun getItem(position: Int): Fragment {
                val crime = crimes[position]
                return CrimeFragment.newInstance(crime.id)
            }

        }

        for ((index, crime) in crimes.withIndex()) {
            if (crime.id == crimeId) {
                viewPager.currentItem = index
                break
            }
        }

    }


}