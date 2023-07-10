package com.tejas.malgangadairy.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tejas.malgangadairy.R

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        title = "Notifications"
    }
}