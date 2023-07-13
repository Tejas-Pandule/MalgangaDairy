package com.tejas.malgangadairy.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.tejas.malgangadairy.MainActivity
import com.tejas.malgangadairy.R

class FlashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash)

         supportActionBar!!.hide()


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        },3000)
    }
}