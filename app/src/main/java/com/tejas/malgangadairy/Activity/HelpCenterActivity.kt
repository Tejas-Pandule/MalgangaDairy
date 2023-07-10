package com.tejas.malgangadairy.Activity


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tejas.malgangadairy.databinding.ActivityHelpCenterBinding


class HelpCenterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHelpCenterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpCenterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Help Center"




    }
}