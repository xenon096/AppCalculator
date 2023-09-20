package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    var lastNumeric=false
    var stateerror=false
    var lastdot=false

    private lateinit var experssion:Expression

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
    //Main onequal-------------------------------------------
    fun onEqual() {
        if (lastNumeric && !stateerror) {
            val txt = binding.dataTv.text.toString()
            experssion = ExpressionBuilder(txt).build()
            try {
                val result = experssion.evaluate()
                binding.resultTv.visibility = View.VISIBLE
                binding.resultTv.text = "=" + result.toString()
            } catch (exp: ArithmeticException) {
                Log.e("evaluate error", exp.toString())
                binding.resultTv.text = "Error"
                stateerror = true
                lastNumeric = false

            }
        }
    }

    // Clear"C"--------------------------
    fun onClearClick(view: View)
    {
        binding.dataTv.text=""
        lastNumeric=false
    }
    //Operator---------------------------
    fun onOperatorClick(view: View)
    {
        if(!stateerror && lastNumeric )
        {
            binding.dataTv.append((view as Button).text)
            lastdot=false
            lastNumeric=false
            onEqual()
        }
    }
    //Digit Click----------------------------------
    fun onDigitClick(view: View)
    {
        if(stateerror)
        {
            binding.dataTv.text=(view as Button).text
            stateerror=false
        }else
        {
            binding.dataTv.append((view as Button).text)
        }
        lastNumeric=true
        onEqual()
    }
    //All Clear"AC"-------------------------------------
    fun onAllclearClick(view: View)
    {
        binding.dataTv.text=""
        binding.resultTv.text=""
        stateerror=false
        lastdot=false
        lastNumeric=false
        binding.resultTv.visibility=View.GONE
    }
    //Backspace------------------------------------
    fun onBackClick(view: View)
    {
        binding.dataTv.text=binding.dataTv.text.toString().dropLast(1)
        try {
            val lastChar=binding.dataTv.text.toString().last()
            if(lastChar.isDigit())
            {
                onEqual()
            }
        }
        catch (e: Exception)
        {
            binding.resultTv.text=""
            binding.resultTv.visibility=View.GONE
            Log.e("last char error",e.toString())
        }
    }
    //Equal"="-----------------------------------------
    fun onEqualClick(view: View)
    {
        onEqual()
        binding.dataTv.text=binding.resultTv.text.toString().drop(1)
    }
}

