package com.daniily000.android.hackyou

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.widget.ImageView
import com.daniily000.android.hackyou.gamelogic.HackMethod
import com.daniily000.android.hackyou.gamelogic.HackableObject
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.post_game_dialog.*
import kotlinx.android.synthetic.main.pre_game_dialog.*
import java.text.DecimalFormat

class GameActivity : AppCompatActivity() {

    companion object {
        private const val BLACKOUT_TIME = 5000L
        private const val DEFAULT_TIME = 60000L
        private const val DEFAULT_TICK = 1000L
        private const val DEFAULT_NAME = "%username%"
        private const val TAG = "GameActivity"
    }

    private var isActivityPresent = false

    private var viewToHackMap: MutableMap<View, HackMethod> = HashMap()
    private var viewSourcesMap: MutableMap<View, Pair<Int, Int>> = HashMap()

    private var name: String = DEFAULT_NAME
    private var time = DEFAULT_TIME
    private var currentTime = time
    private var pts = 0
    private var multiplier = 1
    private var currentHack: HackMethod = HackMethod.values()[0]
    private var currentObject: HackableObject = HackableObject.values()[0]
    private var playing = false
    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        viewToHackMap = object : HashMap<View, HackMethod>() {
            init {
                put(hacks_ptw, HackMethod.PTW)
                put(hacks_handshake, HackMethod.HNDSHK_CAP)
                put(hacks_pixie_dust, HackMethod.PIXIE_DUST)
            }
        }

        viewSourcesMap = object : HashMap<View, Pair<Int, Int>>() {
            init {
                put(hacks_ptw, Pair(R.mipmap.hack_ptw, R.mipmap.hack_ptw_chosen))
                put(hacks_handshake, Pair(R.mipmap.hack_handshake, R.mipmap.hack_handshake_chosen))
                put(hacks_pixie_dust, Pair(R.mipmap.hack_pixie_dust, R.mipmap.hack_pixie_dust_chosen))
            }
        }

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
    }

    override fun onResume() {
        isActivityPresent = true;
        super.onResume()

        // get name and time
        val preGameDialog = object : Dialog(this) {
            override fun onStart() {
                super.onStart()
                pre_game_proceed_button.setOnClickListener {

                    pre_game_name_label.setTextColor(ResourcesCompat.getColor(resources, R.color.colorAccentDark, null))
                    pre_game_time_label.setTextColor(ResourcesCompat.getColor(resources, R.color.colorAccentDark, null))

                    var canStart = true
                    if (pre_game_name.text.toString() == "") {
                        canStart = false
                        pre_game_name_label.setTextColor(ResourcesCompat.getColor(resources, R.color.colorAccentDarkComplementary, null))
                    } else {
                        name = pre_game_name.text.toString()
                    }
                    if (pre_game_time.text.toString() == "") {
                        canStart = false
                        pre_game_time_label.setTextColor(ResourcesCompat.getColor(resources, R.color.colorAccentDarkComplementary, null))
                    }
                    try {
                        time = pre_game_time.text.toString().toLong() * 1000
                        currentTime = time
                        if (time <= 0) {
                            canStart = false
                            pre_game_time_label.setTextColor(ResourcesCompat.getColor(resources, R.color.colorAccentDarkComplementary, null))
                        }
                    } catch (e: NumberFormatException) {
                        canStart = false
                        pre_game_time_label.setTextColor(ResourcesCompat.getColor(resources, R.color.colorAccentDarkComplementary, null))
                    }

                    if (canStart) {
                        startGame()
                        this.cancel()
                    }
                }
            }

            override fun onBackPressed() {
                super.onBackPressed()
                finish()
            }
        }
        preGameDialog.setContentView(R.layout.pre_game_dialog)
        preGameDialog.setCanceledOnTouchOutside(false)
        if (isActivityPresent) preGameDialog.show()

        resetGameValues()
        changeCurrentHack(hacks_ptw)
    }

    override fun onPause() {
        isActivityPresent = false
        super.onPause()
    }

    /** Processes a single tap on main clicker object */
    fun hackTap(v: View) {

        if (!playing) return

        if (currentObject.hack(currentHack) != null) {
            hacking_progress_bar.progress += 1
            if (hacking_progress_bar.progress == hacking_progress_bar.max) {
                approveHack(currentObject.reward)
                changeHackTarget()
            }
        } else {
            failHack()
        }
    }

    /** Changes current hack object to a random one. Resets the progress bar */
    fun changeHackTarget() {
        currentObject = HackableObject.generate()
        hacking_progress_bar.progress = 0
        hacking_progress_bar.max = currentObject.hack(currentHack) ?: 1;
        refreshCurrentHackableObjectView()
    }

    /** Resets game values to initial ones.
     * Should be deprecated - use resetGame instead (not implemented yet) */
    fun resetGameValues() {

        pts = 0;
        multiplier = 1
        currentTime = time
        refreshCounterViews()
    }


    fun approveHack(reward: Int) {
        pts += reward * multiplier
        multiplier++
        refreshCounterViews()
    }

    fun failHack() {
        multiplier = 1;
        refreshCounterViews()
    }

    fun refreshCounterViews() {
        current_pts_text_view.text = pts.toString()
        current_multiplier_text_view.text = "x" + multiplier
    }

    fun refreshTimerView() {
        current_time_text_view.text = (currentTime / 1000).toString()
    }

    fun refreshCurrentHackableObjectView() {
        hackable_object_text_view.text = currentObject.displayName
    }

    fun startGame() {
        playing = true
        refreshTimerView()
        changeHackTarget()
        startCountdown(time)
    }

    fun finishGame() {

        playing = false

        val postGameDialog = object : Dialog(this) {

            override fun onStart() {
                super.onStart()

                post_game_name_text_view.text = name
                post_game_time_text_view.text = (time / 1000).toString()
                post_game_pts_text_view.text = pts.toString()
                post_game_efficiency_text_view.text = DecimalFormat("#.#####").format(pts.toFloat() * 1000 / time)

                post_game_restart_button.setOnClickListener {
                    resetGameValues()
                    startGame()
                    this.cancel()
                }

                post_game_finish_button.setOnClickListener {
                    finish()
                }
            }

            override fun onBackPressed() {
                super.onBackPressed()
                finish()
            }
        }
        postGameDialog.setContentView(R.layout.post_game_dialog)
        postGameDialog.setCanceledOnTouchOutside(false)
        if (isActivityPresent) postGameDialog.show()

    }

    fun changeCurrentHack(v: View) {
        currentHack = viewToHackMap.get(v) ?: return
        hacking_progress_bar.progress = 0
        hacking_progress_bar.max = currentObject.hack(currentHack) ?: 1
        viewSourcesMap.keys.forEach {
            if (it is ImageView) viewSourcesMap.get(it)?.first?.let { it1 -> it.setImageResource(it1) }
        }
        viewSourcesMap.get(v)?.second?.let { it -> if (v is ImageView) v.setImageResource(it) }
    }

    fun startCountdown(time: Long) {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(time, DEFAULT_TICK) {

            override fun onFinish() {
                refreshTimerView()
                finishGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                currentTime = millisUntilFinished
                refreshTimerView()
            }

        }
        countDownTimer?.start()
    }

    fun startBlackout(v: View) {
        startBlackout(BLACKOUT_TIME)
    }

    fun startBlackout(time: Long) {
        countDownTimer?.cancel()
        hackable_object_image_view.isEnabled = false
        val countDownTimer = object : CountDownTimer(time, DEFAULT_TICK) {
            override fun onFinish() {
                startCountdown(currentTime)
                hackable_object_image_view.isEnabled = true
                current_time_text_view.setTextColor(
                        ResourcesCompat.getColor(getResources(), R.color.colorAccentDark, null))
            }

            override fun onTick(millisUntilFinished: Long) {}
        }
        current_time_text_view.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorAccentDarkComplementary, null))

        countDownTimer.start()
    }
}

