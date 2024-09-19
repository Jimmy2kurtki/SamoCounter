package com.lessons.samocounter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lessons.samocounter.money.MoneyCount

class Calculator : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val moneyCount = MoneyCount()
        val resultSimMoney: TextView = findViewById(R.id.textView_result_sim_money)
        val resultMoneySim: TextView = findViewById(R.id.textView_result_money_sim)
        val editTextSimMoney: EditText = findViewById(R.id.editText_sim_money)
        val editTextMoneySim: EditText = findViewById(R.id.editText_money_sim)
        val buttonSimMoney: TextView = findViewById(R.id.textView_button_sim_money)
        val buttonMoneySim: TextView = findViewById(R.id.textView_button_money_sim)

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