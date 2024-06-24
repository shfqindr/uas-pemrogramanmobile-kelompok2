package com.example.macpop_uas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button_editmenu = findViewById<Button>(R.id.button_editmenu)
        button_editmenu.setOnClickListener {
            val intent = Intent(this, EditMenuActivity::class.java)
            startActivity(intent)
        }
        val button_menu = findViewById<Button>(R.id.button_menu)
        button_menu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val button_report = findViewById<Button>(R.id.button_report)
        button_report.setOnClickListener{
           val intent = Intent(this, LaporanActivity::class.java)
            startActivity(intent)
        }

    }
}
