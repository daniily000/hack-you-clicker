package com.daniily000.android.hackyou

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent.*
import android.view.View
import com.daniily000.android.hackyou.util.CountdownThread
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    companion object {
        private val DEFAULT_TIME = 10L;
        private const val TAG = "GameActivity"
    }

    private var pts = 0
    private var multiplier = 1
    private var time = DEFAULT_TIME

    private var playing = false

    private var countdownThread = CountdownThread(DEFAULT_TIME * 1000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        hackable_object_image_view.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event?.action) {
                ACTION_DOWN -> {
                    v?.scaleX = 0.5f
                    v?.scaleY = 0.5f
                    return@OnTouchListener true
                }
                ACTION_UP -> {
                    v?.scaleX = 1f
                    v?.scaleY = 1f
                    v?.performClick()
                    return@OnTouchListener true
                }
            }
            false
        })

        resetGameValues()

    }


    fun hackTap(v: View) {
        if (!playing) {
            startGame()
            changeHackTarget()
        }
        hacking_progress_bar.progress += 1;
        if (hacking_progress_bar.progress == hacking_progress_bar.max) {
            addPtsView(hacking_progress_bar.max)
            hacking_progress_bar.progress = 0
            changeHackTarget()
        }

        Log.i(TAG, "hackTap: progress=${hacking_progress_bar.progress}")
    }

    fun changeHackTarget() {
        // TODO: Set random target
        hacking_progress_bar.max = 10;
    }

    fun resetGameValues() {
        playing = false
        pts = 0;
        multiplier = 1;
        time = DEFAULT_TIME

        refreshCounterViews()
        current_time_text_view.text = time.toString()
        hacking_progress_bar.progress = 0
        resetCountdown()
    }

    fun refreshTimerView() {
        current_time_text_view.text = time.toString()
    }

    fun addPtsView(add: Int) {
        pts += add * multiplier
        multiplier++
        refreshCounterViews()
    }

    fun refreshCounterViews() {
        current_pts_text_view.text = pts.toString()
        current_multiplier_text_view.text = "x" + multiplier
    }

    fun finishGame() {
        resetGameValues()
    }

    fun startGame() {
        countdownThread.start()
        playing = true
    }

    fun resetCountdown() {
        countdownThread = CountdownThread(DEFAULT_TIME.toLong() * 1000)
        countdownThread.setOnTimerEventCallback(object : CountdownThread.OnTimerEventCallback {
            override fun onTimerChanged(timeNow: Long) {
                runOnUiThread {
                    Log.i(TAG, "time=$time")
                    time = timeNow / 1000
                    refreshTimerView()
                }
            }

            override fun onTimerFinished() {
                runOnUiThread {
                    refreshTimerView()
                    finishGame()
                }
            }

        })
    }
}