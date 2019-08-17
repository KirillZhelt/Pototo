package dev.kirillzhelt.pototo

import android.os.CountDownTimer

class PototoTimer(millisInFuture: Int, val onPototoTimerFinish: () -> Unit, val onPototoTimerTick: (p0: Long) -> Unit) {

    companion object {
        const val COUNT_DOWN_INTERVAL: Long = 1000
    }

    var millisInFuture: Int = millisInFuture
        set (value) {
            field = value
            countDownTimer = makeCountDownTimerObject() // make new CountDownTimer because time to count down changed
        }

    private var countDownTimer: CountDownTimer = makeCountDownTimerObject()

    // makes new CountDownTimer according to current parameters
    private fun makeCountDownTimerObject() = object: CountDownTimer(millisInFuture.toLong(), COUNT_DOWN_INTERVAL) {
        override fun onFinish() {
            onPototoTimerFinish()
        }

        override fun onTick(p0: Long) {
            onPototoTimerTick(p0)
        }
    }

    fun start() {
        countDownTimer.start()
    }

    fun cancel() {
        countDownTimer.cancel()
    }
}