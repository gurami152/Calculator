package com.example.calculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.PixelFormat
import android.os.Build
import android.view.*
import android.widget.Button
import kotlinx.android.synthetic.main.layout_floating_window.view.*
import kotlin.math.abs


/**
 * @author aminography
 */
class SimpleFloatingWindow constructor(private val context: Context) {

    private var calculations:Calculations
    private var windowManager: WindowManager? = null
        get() {
            if (field == null) field = (context.getSystemService(WINDOW_SERVICE) as WindowManager)
            return field
        }

    @SuppressLint("InflateParams")
    private var floatView: View =
        LayoutInflater.from(context).inflate(R.layout.layout_floating_window, null)

    private lateinit var layoutParams: WindowManager.LayoutParams

    private var lastX: Int = 0
    private var lastY: Int = 0
    private var firstX: Int = 0
    private var firstY: Int = 0

    private var isShowing = false
    private var touchConsumedByMove = false

    private val onTouchListener = View.OnTouchListener { view, event ->
        val totalDeltaX = lastX - firstX
        val totalDeltaY = lastY - firstY

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
                firstX = lastX
                firstY = lastY
            }
            MotionEvent.ACTION_UP -> {
                view.performClick()
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = event.rawX.toInt() - lastX
                val deltaY = event.rawY.toInt() - lastY
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
                if (abs(totalDeltaX) >= 5 || abs(totalDeltaY) >= 5) {
                    if (event.pointerCount == 1) {
                        layoutParams.x += deltaX
                        layoutParams.y += deltaY
                        touchConsumedByMove = true
                        windowManager?.apply {
                            updateViewLayout(floatView, layoutParams)
                        }
                    } else {
                        touchConsumedByMove = false
                    }
                } else {
                    touchConsumedByMove = false
                }
            }
            else -> {
            }
        }
        touchConsumedByMove
    }

    init {
        with(floatView) {
            closeImageButton.setOnClickListener { dismiss() }
            calculations=Calculations(textView,textView2)
            val button1: Button = findViewById(R.id.button19)
            button1.setOnClickListener{calculations.digitClick("1")}
            button7.setOnClickListener{calculations.acClick()}
            button6.setOnClickListener { calculations.plusMinusClick() }
            button5.setOnClickListener {  calculations.digitClick("00")}
            button4.setOnClickListener { calculations.divideClick() }
            button11.setOnClickListener { calculations.digitClick("7") }
            button10.setOnClickListener { calculations.digitClick("8") }
            button9.setOnClickListener { calculations.digitClick("9") }
            button8.setOnClickListener { calculations.multiplyClick() }
            button15.setOnClickListener { calculations.digitClick("4") }
            button14.setOnClickListener { calculations.digitClick("5") }
            button13.setOnClickListener { calculations.digitClick("6") }
            button12.setOnClickListener { calculations.minusClick()}
            button19.setOnClickListener { calculations.digitClick("1") }
            button18.setOnClickListener { calculations.digitClick("2") }
            button17.setOnClickListener { calculations.digitClick("3") }
            button16.setOnClickListener { calculations.plusClick() }
            button22.setOnClickListener { calculations.digitClick("0") }
            button21.setOnClickListener { calculations.dotClick() }
            button20.setOnClickListener { calculations.solutionClick() }
            button.setOnClickListener { calculations.removeLast() }
        }

        floatView.setOnTouchListener(onTouchListener)

        layoutParams = WindowManager.LayoutParams().apply {
            format = PixelFormat.TRANSLUCENT
            flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            @Suppress("DEPRECATION")
            type = when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ->
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                else -> WindowManager.LayoutParams.TYPE_TOAST
            }

            gravity = Gravity.CENTER
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
    }

    fun show() {
        if (context.canDrawOverlays) {
            dismiss()
            isShowing = true
            windowManager?.addView(floatView, layoutParams)
        }
    }

    private fun dismiss() {
        if (isShowing) {
            windowManager?.removeView(floatView)
            isShowing = false
        }
    }

}