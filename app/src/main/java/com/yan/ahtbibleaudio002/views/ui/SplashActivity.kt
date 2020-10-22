package com.yan.ahtbibleaudio002.views.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.yan.ahtbibleaudio002.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            //Your Code
            val intent = Intent(this@SplashActivity,MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 2000)
    }
}