package dev.kirillzhelt.pototo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
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

        val timerDefaultTime = getString(R.string.default_time_remain).split(":").map { s -> s.toLong() }
        val minutes = timerDefaultTime[0]
        val seconds = timerDefaultTime[1]

        timer = PototoTimer((minutes * 60 + seconds) * 1000,
            ::timerFinish,
            ::timerTick)

        timerTextView = binding.timerTextview
        timerTextView.text = getString(R.string.time_placeholder, minutes, seconds)

        potatoesImageView = binding.potatoesImageview
        potatoesImageView.setOnClickListener(::timerStart)

        cancelButton = binding.cancelButton
        cancelButton.setOnClickListener(::timerCancel)

        return binding.root
    }

    private fun timerStart(v: View) {
        timer.start()
        cancelButton.visibility = View.VISIBLE
    }

    private fun timerTick(p0: Long) {
        timerTextView.text = getTimerTextViewText(p0)
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

    private fun getTimerTextViewText(millis: Long) = getString(R.string.time_placeholder, millis / 60000,
        (millis % 60000) / 1000)

}
