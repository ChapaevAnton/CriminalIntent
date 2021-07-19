package com.W4ereT1ckRtB1tch.criminalintent.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.fragment.app.Fragment
import com.W4ereT1ckRtB1tch.criminalintent.R
import com.W4ereT1ckRtB1tch.criminalintent.data.Crime

class CrimeFragment : Fragment() {

    private lateinit var mCrime: Crime
    private lateinit var mTitleFiled: EditText
    private lateinit var mSolvedField: AppCompatCheckBox
    private lateinit var mDateButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCrime = Crime()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_crime, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTitleFiled = view.findViewById(R.id.crime_title)
        mSolvedField = view.findViewById(R.id.crime_solved)
        mDateButton = view.findViewById(R.id.crime_date)


        mTitleFiled.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mCrime.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        mDateButton.text = mCrime.date.toString()

        mSolvedField.setOnCheckedChangeListener { _, isChecked ->
            mCrime.solved = isChecked
        }

    }

}