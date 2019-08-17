package dev.kirillzhelt.pototo


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

/**
 * A simple [Fragment] subclass.
 *
 */
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        if (preference is MinutesPickerPreference) {
            val dialogFragment = MinutesPickerDialogFragmentCompat.newInstance(preference.key)
            dialogFragment.setTargetFragment(this, 0)
            dialogFragment.show(this.fragmentManager, "android.support.v7.preference" +
                    ".PreferenceFragment.DIALOG")
        } else {
            super.onDisplayPreferenceDialog(preference)
        }
    }
}
