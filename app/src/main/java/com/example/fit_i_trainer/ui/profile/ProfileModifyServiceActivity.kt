package com.example.fit_i_trainer.ui.profile

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.request.ModifyTrainerInfoRequest
import com.example.fit_i_trainer.data.model.response.ModifyTrainerInfoResponse
import com.example.fit_i_trainer.data.service.TrainerService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileModifyServiceActivity :AppCompatActivity(){
var serviceDetail:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_modify_service)
        val name: String = intent.getStringExtra("name").toString()
        val intro: String = intent.getStringExtra("intro").toString()
        val cost : Int = intent.getIntExtra("cost",0).toInt()
        //객체 생성
        val edit : EditText=findViewById(R.id.tv_about_service_context)
        val done = findViewById<Button>(R.id.btn_done)

        done.isEnabled = false

        edit.addTextChangedListener(object:TextWatcher{
            var maxText =""
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                maxText = p0.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                serviceDetail = edit.text.toString()
                done.isEnabled = true
            }
        })
        //완료버튼

        done.setOnClickListener{
            //401오류,,,ㅜㅜ
            val trainerService = RetrofitImpl.getApiClient().create(TrainerService::class.java)
            trainerService.modifyTrainerInfo(
                ModifyTrainerInfoRequest(cost,intro
                    ,name,serviceDetail)
            ).enqueue(object :
                Callback<ModifyTrainerInfoResponse> {
                override fun onResponse(
                    call: Call<ModifyTrainerInfoResponse>,
                    response: Response<ModifyTrainerInfoResponse>
                ) {
                    if (response.isSuccessful) {
                        // 정상적으로 통신이 성공된 경우
                        Log.d("put", "onResponse 성공: " + response.body().toString());
                        //Toast.makeText(this@ProfileActivity, "비밀번호 찾기 성공!", Toast.LENGTH_SHORT).show()

                    } else {
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d("put", "onResponse 실패"+response.code())
                    }
                }

                override fun onFailure(call: Call<ModifyTrainerInfoResponse>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d("put", "onFailure 에러: " + t.message.toString());
                }
            })
            val intent = Intent(this, ProfileActivity::class.java)
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