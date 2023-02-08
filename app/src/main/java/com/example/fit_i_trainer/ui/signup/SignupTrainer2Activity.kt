package com.example.fit_i_trainer.ui.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.service.AccountsService
import com.example.fit_i_trainer.databinding.ActivitySignupCerti2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupTrainer2Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupCerti2Binding

    var schoolEmail: String = ""
    var code: String = ""
    var returnCode: String = "" //api로 부터 받아온 인증코드
    var major: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_certi2)

        major = intent.getStringExtra("major").toString()

        binding = ActivitySignupCerti2Binding.inflate(layoutInflater)

        val etEmailS: EditText = findViewById(R.id.et_emailS)
        val etCodeS: EditText = findViewById(R.id.et_codeS)
        val btnCerti: Button = findViewById(R.id.btn_major_next2)

        btnCerti.isEnabled = false
        btnCerti.text = "인증코드 발급"

        etCodeS.visibility = View.INVISIBLE

        etEmailS.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            //값 변경 시 실행되는 함수
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력값 담기
                schoolEmail = etEmailS.text.toString()


                //stroke 색상변경
                if (schoolEmail.isNotEmpty())
                    etEmailS.setBackgroundResource(R.drawable.edittext_border)
                else
                    etEmailS.setBackgroundResource(R.drawable.edittext_border_not)

                //값 유무에 따른 활성화 여부
                btnCerti.isEnabled = schoolEmail.isNotEmpty()

                btnCerti.setOnClickListener() {
                    if (btnCerti.isEnabled)
                        etCodeS.visibility = View.VISIBLE

                    val accountService =
                        RetrofitImpl.getApiClientWithOutToken().create(AccountsService::class.java)
                    accountService.sendEmail(schoolEmail).enqueue(object : Callback<BaseResponse> {
                        override fun onResponse(
                            call: Call<BaseResponse>,
                            response: Response<BaseResponse>
                        ) {
                            if (response.isSuccessful) {
                                // 정상적으로 통신이 성공된 경우
                                Log.d("post", "onResponse 성공: " + response.body().toString());
                                Toast.makeText(this@SignupTrainer2Activity, "이메일이 전송되었습니다.", Toast.LENGTH_SHORT).show()
                                returnCode = response.body()?.result.toString()
                            } else {
                                // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                Log.d("post", "onResponse 실패" +schoolEmail +response.body())
                            }
                        }

                        override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                            // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                            Log.d("post", "onFailure 에러: " + t.message.toString());
                        }
                    })
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        etCodeS.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            //값 변경 시 실행되는 함수
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //입력값 담기
                code = etCodeS.text.toString()

                if (btnCerti.text == "인증코드 발급")
                    btnCerti.text = "다음"

                //stroke 색상변경
                if (code.isNotEmpty())
                    etCodeS.setBackgroundResource(R.drawable.edittext_border)
                else
                    etCodeS.setBackgroundResource(R.drawable.edittext_border_not)

                btnCerti.setOnClickListener {
                    if(code==returnCode)
                        letgo()
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun letgo() {
        val intent = Intent(this, SignupActivity::class.java)
        intent.putExtra("email", schoolEmail)
        intent.putExtra("major", major)
        startActivity(intent)  // 화면 전환을 시켜줌
        finish()
    }
}