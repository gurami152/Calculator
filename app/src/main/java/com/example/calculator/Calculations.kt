package com.example.calculator

import android.annotation.SuppressLint
import android.widget.TextView

class Calculations(textViewMain: TextView, textViewSecondary: TextView) {

    private var firstNumber: Double? = null
    private var operation:Int? = null // + =0 | - =1 | * =2 | / =3|
    private var previousResult:Double = 0.0
    private var zeroError:Boolean = false
    private var solution: Boolean = false
    private var textMain:TextView = textViewMain
    private var textSecondary:TextView = textViewSecondary

    fun digitClick(digit:String) {
        if((textMain.text=="0")||(textMain.text=="00")||(textMain.text=="0.0")||(solution)||(zeroError)){
            textMain.text=digit
            solution=false
            zeroError=false
        }
        else{
            val a = textMain.text.toString()
            val c = a+digit
            textMain.text = c
        }
    }

    fun removeLast() {
        checkZeroDivideError()
        val a = textMain.text.toString()
        if((a=="0")||(a=="0.0")) {
            return
        }
        if (a.length==1){textMain.text="0"}
        else{
            val b = a.dropLast(1)
            textMain.text= b
        }
    }

    fun dotClick() {
        checkZeroDivideError()
        val a = textMain.text.toString()
        val b = "$a."
        textMain.text = b
    }

    private fun checkZeroDivideError(){
        if(zeroError){
            zeroError=false
            textMain.text="0"
        }
    }

    fun acClick(){
        textMain.text ="0"
        solution = false
        textSecondary.text=""
        firstNumber=null
    }

    fun solutionClick() {
        checkZeroDivideError()
        val result: Double
        val a = textMain.text.toString()
        when(operation){
            0 -> {
                result = previousResult + a.toDouble()
                textMain.text="$result"
            }
            1 -> {
                result = previousResult - a.toDouble()
                textMain.text="$result"
            }
            2 -> {
                result = previousResult * a.toDouble()
                textMain.text="$result"
            }
            3 -> {
                if(a.toDouble()!=0.0) {
                    result = firstNumber!! / a.toDouble()
                    textMain.text="$result"
                } else textMain.text="Деление на 0"
            }
        }
        textSecondary.text=""
        solution=true
        firstNumber=null
    }

    @SuppressLint("SetTextI18n")
    fun plusClick() {
        checkZeroDivideError()
        if(firstNumber==null){
            val a = textMain.text.toString()
            firstNumber = a.toDouble()
            textSecondary.text=textMain.text
            textMain.text=""
            previousResult= firstNumber as Double
        }
        else {
            val a = textSecondary.text.toString()
            val b = textMain.text.toString()
            val result = a.toDouble()+b.toDouble()
            textMain.text="0"
            textSecondary.text="$result"
            previousResult=result
        }
        operation=0
    }

    fun plusMinusClick() {
        val a = textMain.text.toString()
        val c = a.toDouble()
        val b = c*-1
        textMain.text = "$b"
    }

    fun divideClick() {
        checkZeroDivideError()
        if(firstNumber==null){
            val a = textMain.text.toString()
            firstNumber = a.toDouble()
            textSecondary.text=textMain.text
            textMain.text=""
            previousResult= firstNumber as Double
        }
        else {
            val a = textSecondary.text.toString()
            val b = textMain.text.toString()
            val result:Double
            if (b.toDouble()!=0.0){
                result = a.toDouble()/b.toDouble()
                textMain.text="0"
                textSecondary.text=result.toString()
                previousResult=result
            }
            else{ textMain.text="Деление на 0"
                zeroError=true}

        }
        operation=3
    }

    fun multiplyClick() {
        checkZeroDivideError()
        if(firstNumber==null){
            val a = textMain.text.toString()
            firstNumber = a.toDouble()
            textSecondary.text=textMain.text
            textMain.text=""
            previousResult= firstNumber as Double
        }
        else {
            val a = textSecondary.text.toString()
            val b = textMain.text.toString()
            val result = a.toDouble()*b.toDouble()
            textMain.text="0"
            textSecondary.text="$result"
            previousResult=result
        }
        operation=2
    }

    fun minusClick() {
        checkZeroDivideError()
        if(firstNumber==null){
            val a = textMain.text.toString()
            firstNumber = a.toDouble()
            textSecondary.text=textMain.text
            textMain.text=""
            previousResult= firstNumber as Double
        }
        else {
            val a = textSecondary.text.toString()
            val b = textMain.text.toString()
            val result = a.toDouble()+b.toDouble()
            textMain.text="0"
            textSecondary.text="$result"
            previousResult=result
        }
        operation=1
    }
}