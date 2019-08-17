package dev.kirillzhelt.pototo

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.preference.PreferenceDialogFragmentCompat


class TimePickerDialogFragmentCompat: PreferenceDialogFragmentCompat() {

    lateinit var numberPicker: NumberPicker

    companion object {
        const val ARG_KEY = "time"

        fun newInstance(key: String): TimePickerDialogFragmentCompat {
            val fragment = TimePickerDialogFragmentCompat()
            val b = Bundle(1)
            b.putString(ARG_KEY, key)
            fragment.arguments = b

            return fragment
        }

    }

    override fun onBindDialogView(view: View?) {
        super.onBindDialogView(view)

        numberPicker = view!!.findViewById(R.id.edit)

        if (preference is TimePickerPreference) {
            val msTime = (preference as TimePickerPreference).msTime

            numberPicker.minValue = 1000
            numberPicker.maxValue = 2000
            numberPicker.value = 1500
        }
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        val minutes = numberPicker.value

        if (preference is TimePickerPreference) {
            val timePreference = preference as TimePickerPreference
            // This allows the client to ignore the user value.
            if (timePreference.callChangeListener(
                    minutes
                )
            ) {
                // Save the value
                timePreference.msTime = minutes.toLong()
            }
        }
    }
}
