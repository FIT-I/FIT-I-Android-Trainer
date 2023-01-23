package com.example.fit_i_trainer

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ProfileModifyMeActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_modify_me)
        //객체생성
        val edit : EditText = findViewById(R.id.tv_about_me_context)
        val done = findViewById<Button>(R.id.btn_done)
        //메세지 담을 변수
        var message: String=""
        done.isEnabled = false

        //완료버튼
        done.setOnClickListener{
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        //뒤로가기
        val goBack = findViewById<ImageButton>(R.id.ib_back_arrow)
        goBack.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}