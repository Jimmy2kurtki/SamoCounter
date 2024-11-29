package com.lessons.samocounter.dataTransfer

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lessons.samocounter.DBHelper
import com.lessons.samocounter.Data
import com.lessons.samocounter.R
import com.lessons.samocounter.main.MainActivity
import kotlin.collections.toTypedArray
import kotlin.jvm.java
import kotlin.text.replace
import kotlin.text.split

class DataTransferActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_transfer)
        val dbHelper = DBHelper(this)

        val tvDataTransfer = findViewById<TextView>(R.id.tvDataTransfer)
        val etDataTransfer = findViewById<EditText>(R.id.etDataTransfer)

        tvDataTransfer.setOnClickListener{
            val allDataString = etDataTransfer.text.toString()
            val arr = allDataString.split("\n").toTypedArray<String>()
            val dateTransfer = arr[arr.size-1]
            var i = 0
            while(i < arr.size-1){
                val numberSim = arr[i].replace("${i+1}) ", "")
                val data = Data(numberSim, "NORM", dateTransfer)
                dbHelper.addOne(data)
                i++
            }
            etDataTransfer.text.clear()
            Toast.makeText(this, "Данные добавлены в базу", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        try {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
        }
    }
}
