package dev.kirillzhelt.pototo

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.preference.DialogPreference

class MinutesPickerPreference(context: Context, attrs: AttributeSet) : DialogPreference(context, attrs) {

    var msTime: Int = 0
        set(value) {
            field = value

            persistInt(value)
        }

    private val dialogLayoutResId = R.layout.pref_dialog_time

    override fun onGetDefaultValue(a: TypedArray?, index: Int): Any {
        return a!!.getString(index)!!.toInt()
    }

    override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any?) {
        msTime = if (restorePersistedValue) getPersistedInt(msTime) else defaultValue as Int
    }

    override fun getDialogLayoutResource(): Int {
        return dialogLayoutResId
    }
}