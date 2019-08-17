package dev.kirillzhelt.pototo

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.preference.DialogPreference

class TimePickerPreference(context: Context, attrs: AttributeSet) : DialogPreference(context, attrs) {

    var msTime: Long = 25 * 60 * 1000
        set(value) {
            field = value

            persistLong(value)
        }

    private val dialogLayoutResId = R.layout.pref_dialog_time

    override fun onGetDefaultValue(a: TypedArray?, index: Int): Any {
        return a!!.getString(index)!!.toLong()
    }

    override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any?) {
        msTime = if (restorePersistedValue) getPersistedLong(msTime) else defaultValue as Long
    }

    override fun getDialogLayoutResource(): Int {
        return dialogLayoutResId
    }
}