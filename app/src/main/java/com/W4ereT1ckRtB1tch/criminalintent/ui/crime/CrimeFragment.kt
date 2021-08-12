package com.W4ereT1ckRtB1tch.criminalintent.ui.crime

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
import com.W4ereT1ckRtB1tch.criminalintent.data.CrimeLab
import java.util.*

class CrimeFragment : Fragment() {

    private var mCrime: Crime? = null
    private lateinit var mTitleFiled: EditText
    private lateinit var mSolvedField: AppCompatCheckBox
    private lateinit var mDateButton: Button

    companion object {

        private const val ARG_CRIME_ID = "crime_id"

        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)
            val fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID

        mCrime = CrimeLab[requireActivity()]?.getCrime(crimeId)
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

        mCrime?.let { mTitleFiled.setText(it.title) }
        mTitleFiled.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mCrime?.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        mDateButton.text = mCrime?.date.toString()

        mCrime?.let { mSolvedField.isChecked = it.solved }
        mSolvedField.setOnCheckedChangeListener { _, isChecked ->
            mCrime?.solved = isChecked
        }

    }

}