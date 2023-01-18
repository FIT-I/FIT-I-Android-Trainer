package com.example.fit_i_trainer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ProfileModifyCostActivity:AppCompatActivity() {
    private lateinit var buttonNext : Button
    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_modify_cost)

        //뒤로가기
        val goBack = findViewById<ImageButton>(R.id.back_arrow)
        goBack.setOnClickListener{
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}