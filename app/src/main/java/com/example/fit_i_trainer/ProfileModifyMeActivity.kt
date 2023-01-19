package com.example.fit_i_trainer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ProfileModifyMeActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_modify_me)
        //완료버튼
        val done = findViewById<Button>(R.id.button_done)
        done.setOnClickListener{
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        //뒤로가기
        val goBack = findViewById<ImageButton>(R.id.back_arrow)
        goBack.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}