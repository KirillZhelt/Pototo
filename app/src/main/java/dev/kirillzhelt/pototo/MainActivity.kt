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

        timerTextView = findViewById(R.id.timer_textview)

        // TODO: get default remain time from resources and use it here
        timer = PototoTimer(1 * 60 * 1000,
            ::timerFinish,
            ::timerTick)

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
        // TODO: fix hard-coded string
        timerTextView.text = "${p0 / 60000}:${(p0 % 60000) / 1000}"
    }

    private fun timerFinish() {
        timerTextView.text = "now you are free (really not)"
        cancelButton.visibility = View.INVISIBLE
    }

    private fun timerCancel(v: View) {
        // TODO: update time
        timer.cancel()
        cancelButton.visibility = View.INVISIBLE
    }
}
