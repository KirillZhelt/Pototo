package dev.kirillzhelt.pototo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity: AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var potatoesImageView: ImageView
    private lateinit var cancelButton: Button

    private lateinit var timer: PototoTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timerDefaultTime = getString(R.string.default_time_remain).split(":").map { s -> s.toLong() }
        val minutes = timerDefaultTime[0]
        val seconds = timerDefaultTime[1]

        timer = PototoTimer((minutes * 60 + seconds) * 1000,
            ::timerFinish,
            ::timerTick)

        timerTextView = findViewById(R.id.timer_textview)
        timerTextView.text = getString(R.string.time_placeholder, minutes, seconds)

        potatoesImageView = findViewById(R.id.potatoes_imageview)
        potatoesImageView.setOnClickListener(::timerStart)

        cancelButton = findViewById(R.id.cancel_button)
        cancelButton.setOnClickListener(::timerCancel)
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
