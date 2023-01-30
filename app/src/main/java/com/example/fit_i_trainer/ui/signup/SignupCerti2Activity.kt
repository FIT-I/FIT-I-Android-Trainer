package com.example.fit_i_trainer.ui.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.databinding.ActivitySignupCerti2Binding
import com.example.fit_i_trainer.ui.login.LoginSplashActivity

class SignupCerti2Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupCerti2Binding

    var schoolEmail: String=""
    var code:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_certi2)

        binding = ActivitySignupCerti2Binding.inflate(layoutInflater)

        val etEmailS : EditText= findViewById(R.id.et_emailS)
        val etCodeS :EditText = findViewById(R.id.et_codeS)
        val btnCerti : Button = findViewById(R.id.btn_major_next2)

        btnCerti.isEnabled = false
        btnCerti.text="인증코드 발급"

        etCodeS.visibility= View.INVISIBLE

        etEmailS.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            //값 변경 시 실행되는 함수
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력값 담기
                schoolEmail = etEmailS.text.toString()

                //값 유무에 따른 활성화 여부
                btnCerti.isEnabled = schoolEmail.isNotEmpty()

                btnCerti.setOnClickListener(){
                    if(btnCerti.isEnabled)
                        etCodeS.visibility= View.VISIBLE}
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        etCodeS.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            //값 변경 시 실행되는 함수
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력값 담기
                code = etCodeS.text.toString()
                if (btnCerti.text=="인증코드 발급")
                    btnCerti.text="다음"
                btnCerti.setOnClickListener {
                    letgo()
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun letgo() {
        val intent = Intent(this, LoginSplashActivity::class.java)
        startActivity(intent)  // 화면 전환을 시켜줌
        finish()
        Toast.makeText(this, schoolEmail + "summit", Toast.LENGTH_SHORT).show()
    }
}