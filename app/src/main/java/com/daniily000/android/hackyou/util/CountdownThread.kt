package com.daniily000.android.hackyou.util

/**
 * CountdownThread counts down the time given (surprising?).
 * Thread can be stopped by default Java Interruption system.
 * Cannot be paused (yet).
 *
 * @param time time needed to reach 0 in ms
 * @param interval intervals in ms to call CountdownThread.OnTimerEventCallback#onTimerChanged(Long). 1000 by default
 */
// TODO: Add pausing mechanism
// TODO: Rewrite as AsyncTask?
// TODO: Deprecate to use android.os.CountDownTimer instead?
@Deprecated("Use android.os.CountDownTimer instead")
class CountdownThread(private var time: Long, private var interval: Long): Thread() {

    constructor(time: Long): this(time, 1000)

    private var onTimerEventCallback: OnTimerEventCallback? = null

    override fun run() {

        while (interval <= time) {
            try {
                Thread.sleep(interval)
                time -= interval
                onTimerEventCallback?.onTimerChanged(time)
            } catch (e: InterruptedException) {
                finish()
            }
        }
        onTimerEventCallback?.onTimerFinished()
        finish()
    }

    interface OnTimerEventCallback {

        /**
         *
         */
        fun onTimerChanged(timeNow: Long)
        fun onTimerFinished()
    }

    /**
     * In case of upgrading the class
     */
    fun finish() {
        return
    }

    public fun setOnTimerEventCallback(callback: OnTimerEventCallback) {
        onTimerEventCallback = callback
    }

}