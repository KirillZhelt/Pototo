package dev.kirillzhelt.pototo

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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


/**
 * A simple [Fragment] subclass.
 *
 *
 */
class TimerFragment : Fragment() {

    private lateinit var timerTextView: TextView
    private lateinit var potatoesImageView: ImageView
    private lateinit var cancelButton: Button

    private lateinit var timer: PototoTimer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentTimerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_timer, container, false)

        setHasOptionsMenu(true)

        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)

        try {
            val timerDefaultTime = sharedPreferences.getInt("timer_minutes", 25)

            timer = PototoTimer(timerDefaultTime * 60 * 1000,
                ::timerFinish,
                ::timerTick)

            timerTextView = binding.timerTextview
            timerTextView.text = getString(R.string.time_placeholder, timerDefaultTime, 0)

            potatoesImageView = binding.potatoesImageview
            potatoesImageView.setOnClickListener(::timerStart)

            cancelButton = binding.cancelButton
            cancelButton.setOnClickListener(::timerCancel)
        } catch (e: Exception) {
            Log.i("hhh", "$e")
        }



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
        timerTextView.text = getTimerTextViewText(p0.toInt())
    }

    private fun timerFinish() {
        timerTextView.text = getString(R.string.timer_finish_text)
        cancelButton.visibility = View.INVISIBLE
    }

    private fun timerCancel(v: View) {
        timer.cancel()
        cancelButton.visibility = View.INVISIBLE

        timerTextView.text = getTimerTextViewText(timer.millisInFuture)
    }

    private fun getTimerTextViewText(millis: Int) = getString(R.string.time_placeholder, millis / 60000,
        (millis % 60000) / 1000)

}
