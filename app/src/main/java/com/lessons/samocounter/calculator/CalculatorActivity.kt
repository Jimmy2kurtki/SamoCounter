package com.lessons.samocounter.calculator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lessons.samocounter.main.MainActivity
import com.lessons.samocounter.R
import com.lessons.samocounter.MoneyCount
import kotlin.jvm.java
import kotlin.text.isEmpty
import kotlin.text.toInt

class   CalculatorActivity : AppCompatActivity() {

    private lateinit var resultSimMoney: TextView
    private lateinit var resultMoneySim: TextView
    private lateinit var buttonSimMoney: TextView
    private lateinit var buttonMoneySim: TextView
    private lateinit var editTextSimMoney: EditText
    private lateinit var editTextMoneySim: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val moneyCount = MoneyCount()
        initAll()

        buttonSimMoney.setOnClickListener{
            var stringSim: String = editTextSimMoney.text.toString()
            if (stringSim.isEmpty()){

            } else {
                var intSim: Int = stringSim.toInt()
                resultSimMoney.text = moneyCount.moneyCount(intSim,false).toString()
            }
        }

        buttonMoneySim.setOnClickListener{
            var stringMoney: String = editTextMoneySim.text.toString()
            if (stringMoney.isEmpty()){

            } else {
                var intMoney: Int = stringMoney.toInt()
                resultMoneySim.text = moneyCount.simCount(intMoney).toString()
            }
        }
    }

    private fun initAll(){
        resultSimMoney = findViewById(R.id.textView_result_sim_money)
        resultMoneySim = findViewById(R.id.textView_result_money_sim)
        editTextSimMoney = findViewById(R.id.editText_sim_money)
        editTextMoneySim = findViewById(R.id.editText_money_sim)
        buttonSimMoney = findViewById(R.id.textView_button_sim_money)
        buttonMoneySim = findViewById(R.id.textView_button_money_sim)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            // Handle exception if needed
        }
    }
}