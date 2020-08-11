package com.example.calculator

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var simpleFloatingWindow: SimpleFloatingWindow
    private lateinit var calculations:Calculations

    // переменные для вычисления

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        closeImageButton.setOnClickListener{
            if (canDrawOverlays) {
                simpleFloatingWindow.show()
            } else {
                startManageDrawOverlaysPermission()
            }
        }
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
        textView.text = "0"
        simpleFloatingWindow = SimpleFloatingWindow(applicationContext)

    }

    private fun startManageDrawOverlaysPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:${applicationContext.packageName}")
            ).let {
                startActivityForResult(it, REQUEST_CODE_DRAW_OVERLAY_PERMISSION)
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_DRAW_OVERLAY_PERMISSION = 5
    }
}

