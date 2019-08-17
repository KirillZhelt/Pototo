package dev.kirillzhelt.pototo

import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.preference.PreferenceDialogFragmentCompat


class MinutesPickerDialogFragmentCompat: PreferenceDialogFragmentCompat() {

    lateinit var numberPicker: NumberPicker

    companion object {
        fun newInstance(key: String): MinutesPickerDialogFragmentCompat {
            val fragment = MinutesPickerDialogFragmentCompat()
            val b = Bundle(1)
            b.putString(ARG_KEY, key)
            fragment.arguments = b

            return fragment
        }

    }

    override fun onBindDialogView(view: View?) {
        super.onBindDialogView(view)

        numberPicker = view!!.findViewById(R.id.edit)

        if (preference is MinutesPickerPreference) {
            val msTime = (preference as MinutesPickerPreference).msTime

            numberPicker.minValue = 1
            numberPicker.maxValue = 59
            numberPicker.value = msTime
        }
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        val minutes = numberPicker.value

        if (preference is MinutesPickerPreference) {
            val timePreference = preference as MinutesPickerPreference
            // This allows the client to ignore the user value.
            if (timePreference.callChangeListener(
                    minutes
                )
            ) {
                // Save the value
                timePreference.msTime = minutes
            }
        }
    }
}
