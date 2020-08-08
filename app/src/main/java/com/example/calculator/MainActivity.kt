package com.example.calculator

import android.annotation.SuppressLint
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
    private var solution: Boolean = false

    // переменные для вычисления
    private var firstNumber: Double? = null
    private var operation:Int? = null // + =0 | - =1 | * =2 | / =3|
    private var previousResult:Double = 0.0
    private var zeroError:Boolean = false
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
        val button1: Button = findViewById(R.id.button19)
        button1.setOnClickListener{digitClick("1")}
        button7.setOnClickListener{acClick()}
        button6.setOnClickListener { plusMinusClick() }
        button5.setOnClickListener {  digitClick("00")}
        button4.setOnClickListener { divideClick() }
        button11.setOnClickListener { digitClick("7") }
        button10.setOnClickListener { digitClick("8") }
        button9.setOnClickListener { digitClick("9") }
        button8.setOnClickListener { multiplyClick() }
        button15.setOnClickListener { digitClick("4") }
        button14.setOnClickListener { digitClick("5") }
        button13.setOnClickListener { digitClick("6") }
        button12.setOnClickListener { minusClick()}
        button19.setOnClickListener { digitClick("1") }
        button18.setOnClickListener { digitClick("2") }
        button17.setOnClickListener { digitClick("3") }
        button16.setOnClickListener { plusClick() }
        button22.setOnClickListener { digitClick("0") }
        button21.setOnClickListener { dotClick() }
        button20.setOnClickListener { solutionClick() }
        button.setOnClickListener { removeLast() }
        textView.text = "0"
        simpleFloatingWindow = SimpleFloatingWindow(applicationContext)
    }

    private fun pipEnter() {

    }

    private fun removeLast() {
        checkZeroDivideError()
        val a = textView.text.toString()
        if((a=="0")||(a=="0.0")) {
            return
        }
        if (a.length==1){textView.text="0"}
        else{
            val b = a.dropLast(1)
            textView.text= b
        }
    }

    private fun dotClick() {
        checkZeroDivideError()
        val a = textView.text.toString()
        val b = "$a."
        textView.text = b
    }

    private fun solutionClick() {
        checkZeroDivideError()
        val result: Double
        val a = textView.text.toString()
        when(operation){
            0 -> {
                result = previousResult + a.toDouble()
                textView.text="$result"
            }
            1 -> {
                result = previousResult - a.toDouble()
                textView.text="$result"
            }
            2 -> {
                result = previousResult * a.toDouble()
                textView.text="$result"
            }
            3 -> {
                if(a.toDouble()!=0.0) {
                    result = firstNumber!! / a.toDouble()
                    textView.text="$result"
                } else textView.text="Деление на 0"
            }
        }
        textView2.text=""
        solution=true
        firstNumber=null
    }

    @SuppressLint("SetTextI18n")
    private fun plusClick() {
        checkZeroDivideError()
        if(firstNumber==null){
            val a = textView.text.toString()
            firstNumber = a.toDouble()
            textView2.text=textView.text
            textView.text=""
            previousResult= firstNumber as Double
        }
        else {
            val a = textView2.text.toString()
            val b = textView.text.toString()
            val result = a.toDouble()+b.toDouble()
            textView.text="0"
            textView2.text="$result"
            previousResult=result
        }
        operation=0
    }

    private fun plusMinusClick() {
        val a = textView.text.toString()
        val c = a.toDouble()
        val b = c*-1
        textView.text = "$b"
    }

    private fun divideClick() {
        checkZeroDivideError()
        if(firstNumber==null){
            val a = textView.text.toString()
            firstNumber = a.toDouble()
            textView2.text=textView.text
            textView.text=""
            previousResult= firstNumber as Double
        }
        else {
            val a = textView2.text.toString()
            val b = textView.text.toString()
            val result:Double
            if (b.toDouble()!=0.0){
                result = a.toDouble()/b.toDouble()
                textView.text="0"
                textView2.text=result.toString()
                previousResult=result
            }
            else{ textView.text="Деление на 0"
                    zeroError=true}

        }
        operation=3
    }

    private fun multiplyClick() {
        checkZeroDivideError()
        if(firstNumber==null){
            val a = textView.text.toString()
            firstNumber = a.toDouble()
            textView2.text=textView.text
            textView.text=""
            previousResult= firstNumber as Double
        }
        else {
            val a = textView2.text.toString()
            val b = textView.text.toString()
            val result = a.toDouble()*b.toDouble()
            textView.text="0"
            textView2.text="$result"
            previousResult=result
        }
        operation=2
    }

    private fun minusClick() {
        checkZeroDivideError()
        if(firstNumber==null){
            val a = textView.text.toString()
            firstNumber = a.toDouble()
            textView2.text=textView.text
            textView.text=""
            previousResult= firstNumber as Double
        }
        else {
            val a = textView2.text.toString()
            val b = textView.text.toString()
            val result = a.toDouble()+b.toDouble()
            textView.text="0"
            textView2.text="$result"
            previousResult=result
        }
        operation=1
    }

    private fun checkZeroDivideError(){
        if(zeroError){
            zeroError=false
            textView.text="0"
        }
    }

    private fun digitClick(digit:String) {
        if((textView.text=="0")||(textView.text=="00")||(textView.text=="0.0")||(solution)||(zeroError)){
            textView.text=digit
            solution=false
            zeroError=false
        }
        else{
            val a = textView.text.toString()
            val c = a+digit
            textView.text = c
        }
    }

    private fun acClick(){
        textView.text ="0"
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

