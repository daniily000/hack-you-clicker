package com.daniily000.android.hackyou.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.daniily000.android.hackyou.R

class MenuButton(context: Context, attrs: AttributeSet) : android.support.v7.widget.AppCompatTextView(context, attrs) {


    var highlightingColor: Int
    var defaultColor: Int

    init {
        val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.MenuButton,
                0,
                0
        )
        highlightingColor = typedArray.getColor(R.styleable.MenuButton_highlightingColor, currentTextColor)
        defaultColor = currentTextColor
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                setTextColor(highlightingColor)
                return true
            }
            MotionEvent.ACTION_UP -> {
                setTextColor(defaultColor)
                performClick()
                return true
            }
        }
        return false
    }

}
