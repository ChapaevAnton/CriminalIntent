package com.W4ereT1ckRtB1tch.criminalintent.ui.fragment.crime

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.format.DateFormat.getDateFormat
import android.text.format.DateFormat.getTimeFormat
import android.util.Log
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
import com.W4ereT1ckRtB1tch.criminalintent.ui.dialog.DatePickerFragment
import com.W4ereT1ckRtB1tch.criminalintent.ui.dialog.TimePickerFragment
import java.text.DateFormat
import java.util.*

class CrimeFragment : Fragment() {

    private lateinit var mCrime: Crime
    private lateinit var mTitleFiled: EditText
    private lateinit var mSolvedField: AppCompatCheckBox
    private lateinit var mDateButton: Button
    private lateinit var mTimeButton: Button
    private lateinit var dateFormat: DateFormat
    private lateinit var timeFormat: DateFormat

    companion object {

        private const val ARG_CRIME_ID = "crime_id"
        private const val DIALOG_DATE = "DialogDate"
        private const val DIALOG_TIME = "DialogTime"
        private const val REQUEST_DATE = 0
        private const val REQUEST_TIME = 1

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
        dateFormat = getDateFormat(requireActivity())
        timeFormat = getTimeFormat(requireActivity())
        CrimeLab[requireActivity()]?.getCrime(crimeId).let {
            if (it != null) {
                mCrime = it
            }
        }
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
        mTimeButton = view.findViewById(R.id.crime_time)

        mTitleFiled.setText(mCrime.title)
        mTitleFiled.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mCrime.title = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        updateDateTime()
        mDateButton.setOnClickListener {
            val fragmentManager = parentFragmentManager
            val dialog = DatePickerFragment.newInstance(mCrime.date)
            // FIXME: 18.08.2021 deprecated
            dialog.setTargetFragment(this, REQUEST_DATE)
            dialog.show(fragmentManager, DIALOG_DATE)
        }
        mTimeButton.setOnClickListener {

            val fragmentManager = parentFragmentManager
            val dialog = TimePickerFragment.newInstance(mCrime.time)
            // FIXME: 18.08.2021 deprecated
            dialog.setTargetFragment(this, REQUEST_TIME)
            dialog.show(fragmentManager, DIALOG_TIME)
        }

        mSolvedField.isChecked = mCrime.solved
        mSolvedField.setOnCheckedChangeListener { _, isChecked ->
            mCrime.solved = isChecked
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return

        Log.d("TAG", "onActivityResult: $requestCode")
        Log.d("TAG", "onActivityResult: $resultCode")
        when (requestCode) {
            REQUEST_DATE -> {
                val date = data?.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
                mCrime.date = date
                updateDateTime()
            }

            REQUEST_TIME -> {
                val time = data?.getSerializableExtra(TimePickerFragment.EXTRA_TIME) as Date
                mCrime.time = time
                Log.d("TAG", "onActivityResult: $time")
                updateDateTime()
            }
        }
    }

    private fun updateDateTime() {
        mDateButton.text = dateFormat.format(mCrime.date)
        mTimeButton.text = timeFormat.format(mCrime.time)
    }

}