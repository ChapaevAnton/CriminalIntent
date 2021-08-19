package com.W4ereT1ckRtB1tch.criminalintent.ui.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import com.W4ereT1ckRtB1tch.criminalintent.R
import java.util.*

class TimePickerFragment : DialogFragment() {

    // TODO: 18.08.2021 needs to transfer the selected time to CrimeFragment and Crime

    companion object {
        private const val ARC_TIME = "time"
        const val EXTRA_TIME = "com.android.criminal_intent.time"

        fun newInstance(time: Date): TimePickerFragment {
            val args = Bundle()
            args.putSerializable(ARC_TIME, time)
            val fragment = TimePickerFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val time = arguments?.getSerializable(ARC_TIME) as Date
        val calendar = Calendar.getInstance()
        calendar.time = time

        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val view = LayoutInflater.from(requireActivity()).inflate(R.layout.dialog_time, null, false)

        val timePicker: TimePicker = view.findViewById(R.id.dialog_time_picker)
        timePicker.setIs24HourView(true)
        timePicker.let {
            it.hour = hour
            it.minute = minute
        }

        return AlertDialog.Builder(requireActivity())
            .setView(view)
            .setTitle(R.string.time_picker_title)
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                val selectedTime = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, timePicker.hour)
                    set(Calendar.MINUTE, timePicker.minute)
                }.time
                setResult(Activity.RESULT_OK, selectedTime)
            }
            .create()
    }

    // FIXME: 18.08.2021 deprecated
    private fun setResult(resultCode: Int, time: Date) {
        if (targetFragment == null) return
        val intent = Intent()
        intent.putExtra(EXTRA_TIME, time)
        targetFragment?.onActivityResult(targetRequestCode, resultCode, intent)
    }

}