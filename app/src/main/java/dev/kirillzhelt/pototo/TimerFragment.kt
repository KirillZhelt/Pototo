package dev.kirillzhelt.pototo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import dev.kirillzhelt.pototo.databinding.FragmentTimerBinding
import kotlin.properties.Delegates


/**
 * A simple [Fragment] subclass.
 *
 *
 */
class TimerFragment : Fragment() {
    // TODO: pick defaultTimerTime from settings for new timer

    private lateinit var timerTextView: TextView
    private lateinit var potatoesImageView: ImageView
    private lateinit var cancelButton: Button

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var timer: PototoTimer

    private lateinit var timerFinishText: String
    private lateinit var timeFormat: String

    private var timerDefaultTime by Delegates.notNull<Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentTimerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_timer, container, false)

        setHasOptionsMenu(true)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        timerDefaultTime = sharedPreferences.getInt("timer_minutes", getString(R.string.default_time_remain).toInt())

        timerFinishText = getString(R.string.timer_finish_text)
        timeFormat = getString(R.string.time_format)

        timer = PototoTimer(timerDefaultTime * 60 * 1000,
            ::timerFinish,
            ::timerTick)

        timerTextView = binding.timerTextview
        timerTextView.text = timeFormat.format(timerDefaultTime, 0)

        potatoesImageView = binding.potatoesImageview
        potatoesImageView.setOnClickListener(::timerStart)

        cancelButton = binding.cancelButton
        cancelButton.setOnClickListener(::timerCancel)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.settingsFragment) {
            val intent = Intent(activity, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }

        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun timerStart(v: View) {
        timer.start()
        cancelButton.visibility = View.VISIBLE
    }

    private fun timerTick(p0: Long) {
        if (isAdded && isVisible && userVisibleHint)
            timerTextView.text = getTimerTextViewText(p0.toInt())
    }

    override fun onStart() {
        super.onStart()

        timer.millisInFuture = sharedPreferences.getInt("timer_minutes", getString(R.string.default_time_remain).toInt())
        userVisibleHint = true
    }

    override fun onStop() {
        super.onStop()

        userVisibleHint = false
    }

    private fun timerFinish() {
        timerTextView.text = timerFinishText
        cancelButton.visibility = View.INVISIBLE
    }

    private fun timerCancel(v: View) {
        timer.cancel()
        cancelButton.visibility = View.INVISIBLE

        timerTextView.text = getTimerTextViewText(timer.millisInFuture)
    }

    private fun getTimerTextViewText(millis: Int) = timeFormat.format(millis / 60000,
        (millis % 60000) / 1000)
}
