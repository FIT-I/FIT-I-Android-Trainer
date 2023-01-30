package com.example.fit_i_trainer

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileAboutMeActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState :  Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_about_me)
        val context = findViewById<TextView>(R.id.tv_about_me_context)
        if(intent.hasExtra("contextMe")){
            context.text=intent.getStringExtra("contextMe")
        }


        val goBack = findViewById<ImageButton>(R.id.ib_back_arrow)
        goBack.setOnClickListener{
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}