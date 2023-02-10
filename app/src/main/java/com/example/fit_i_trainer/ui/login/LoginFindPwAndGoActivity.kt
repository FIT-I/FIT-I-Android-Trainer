package com.example.fit_i_trainer.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.databinding.ActivityFindPwBinding
import com.example.fit_i_trainer.databinding.ActivityLoginFindPwAndGoBinding
import com.example.fit_i_trainer.ui.main.MainActivity

class LoginFindPwAndGoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginFindPwAndGoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFindPwAndGoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val pwShow = findViewById<TextView>(R.id.et_pw_show)
        pwShow.text=intent.getStringExtra("pw")

        val btnDone = findViewById<Button>(R.id.btn_done)

        btnDone.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)  // 인텐트를 생성해줌,
            startActivity(intent)  // 화면 전환을 시켜줌
            finish()
        }
    }
}