package dev.kirillzhelt.pototo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timerTextView: TextView = findViewById(R.id.timer_textview)

        val timer = object: CountDownTimer(5 * 60 * 1000, 1000) {
            override fun onTick(p0: Long) {
                timerTextView.text = "${p0 / 60000}:${(p0 % 60000) / 1000}"
            }

            override fun onFinish() {
                timerTextView.text = "now you are free (really not)"
            }
        }

        timer.start()
    }
}
