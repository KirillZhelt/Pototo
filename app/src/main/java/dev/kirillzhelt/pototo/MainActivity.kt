package dev.kirillzhelt.pototo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timerTextView: TextView = findViewById(R.id.timer_textview)

        val timer: PototoTimer = PototoTimer(1 * 60 * 1000,
            { timerTextView.text = "now you are free (really not)" },
            { p0 -> timerTextView.text = "${p0 / 60000}:${(p0 % 60000) / 1000}" })

        val potatoesImageView: ImageView = findViewById(R.id.potatoes_imageview)
        potatoesImageView.setOnClickListener { timer.start() }

        val cancelButton: Button = findViewById(R.id.cancel_button)
        cancelButton.setOnClickListener { timer.cancel() }
    }
}
