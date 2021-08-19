package com.W4ereT1ckRtB1tch.criminalintent.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.W4ereT1ckRtB1tch.criminalintent.R
import java.util.*

class DatePickerFragment : DialogFragment() {


    companion object {
        private const val ARC_DATE = "date"
        const val EXTRA_DATE = "com.android.criminal_intent.date"

        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle()
            args.putSerializable(ARC_DATE, date)
            val fragment = DatePickerFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val date = arguments?.getSerializable(ARC_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val view = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_date, null, false)

        val datePicker: DatePicker = view.findViewById(R.id.dialog_date_picker)
        datePicker.init(year, month, day, null)

        return AlertDialog
            .Builder(requireActivity())
            .setView(view)
            .setTitle(R.string.date_picker_title)
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                val selectedDate = GregorianCalendar(datePicker.year, datePicker.month, datePicker.dayOfMonth).time
                setResult(Activity.RESULT_OK, selectedDate)
            }
            .create()
    }


    // FIXME: 18.08.2021 deprecated
    private fun setResult(resultCode: Int, date: Date) {
        if (targetFragment == null) return
        val intent = Intent()
        intent.putExtra(EXTRA_DATE, date)
        targetFragment?.onActivityResult(targetRequestCode, resultCode, intent)
    }
}