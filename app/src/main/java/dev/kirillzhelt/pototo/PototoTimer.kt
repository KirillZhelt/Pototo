package dev.kirillzhelt.pototo

import android.os.CountDownTimer

class PototoTimer(millisInFuture: Long, val onPototoTimerFinish: () -> Unit, val onPototoTimerTick: (p0: Long) -> Unit) {

    companion object {
        const val COUNT_DOWN_INTERVAL: Long = 1000
    }

    var millisInFuture: Long = millisInFuture
        set (value) {
            field = value
            countDownTimer = makeCountDownTimerObject()
        }

    private var countDownTimer: CountDownTimer = makeCountDownTimerObject()

    private fun makeCountDownTimerObject() = object: CountDownTimer(millisInFuture, COUNT_DOWN_INTERVAL) {
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