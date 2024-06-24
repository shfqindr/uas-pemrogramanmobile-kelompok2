package com.example.macpop_uas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Tampilkan splash screen selama 3 detik
        Handler(Looper.getMainLooper()).postDelayed({
            // Pindahkan ke aktivitas utama setelah 3 detik
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}