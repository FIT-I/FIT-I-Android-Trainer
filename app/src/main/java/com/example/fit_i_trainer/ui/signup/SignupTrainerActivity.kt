package com.example.fit_i_trainer.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.databinding.ActivitySignupCertiBinding

class SignupTrainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupCertiBinding

    //메시지 담을 변수
    var major: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_certi)

        //객체 생성
        val etMajor: EditText = findViewById(R.id.et_major)
        val btnNext: Button = findViewById(R.id.btn_major_next)

        //버튼 비활성화
        btnNext.isEnabled = false

        //EditText 값 있을때만 버튼 활성화
        etMajor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            //값 변경 시 실행되는 함수
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력값 담기
                major = etMajor.text.toString()

                //값 유무에 따른 활성화 여부
                btnNext.isEnabled = major.isNotEmpty() //있다면 true 없으면 false
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        //회원가입하기
        //버튼 이벤트
        btnNext.setOnClickListener {
            val intent = Intent(this, SignupTrainer2Activity::class.java)
            intent.putExtra("major",major)
            startActivity(intent)  // 화면 전환을 시켜줌
            finish()
            Toast.makeText(this, major + "signUp", Toast.LENGTH_SHORT).show()
        }
    }
}