package com.example.fit_i_trainer.ui.profile.modify

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
import com.example.fit_i_trainer.data.model.response.GetTrainerInfoResponse
import com.example.fit_i_trainer.data.service.TrainerService
import com.example.fit_i_trainer.databinding.ActivityProfileModifyServiceBinding
import com.example.fit_i_trainer.ui.profile.ProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileModifyServiceActivity :AppCompatActivity(){
    private lateinit var binding: ActivityProfileModifyServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileModifyServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.getParcelableExtra<ModifyTrainerInfoRequest>("modify")
        var costHour: String? = data?.costHour
        var intro: String? = data?.intro
        var name: String? = data?.name
        var serviceDetail: String? = data?.serviceDetail

        //Log.d("post", ModifyTrainerInfoRequest(costHour,intro,name,serviceDetail).toString())

        var modiServiceDetail : String=""

        //객체 생성
        val edit : EditText = findViewById(R.id.tv_about_service_context)
        val done = findViewById<Button>(R.id.btn_done)
        edit.setText(serviceDetail)
        done.isEnabled = false

        edit.addTextChangedListener(object:TextWatcher{
            var maxText =""
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                maxText = p0.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                modiServiceDetail = edit.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                done.isEnabled = true
            }
        })

        //완료버튼
        done.setOnClickListener{
            val trainerService = RetrofitImpl.getApiClient().create(TrainerService::class.java)
            trainerService.modifyTrainerInfo(
                ModifyTrainerInfoRequest(costHour,intro
                ,name,modiServiceDetail)
            ).enqueue(object :
                Callback<GetTrainerInfoResponse> {
                override fun onResponse(
                    call: Call<GetTrainerInfoResponse>,
                    response: Response<GetTrainerInfoResponse>
                ) {
                    if (response.isSuccessful) {
                        // 정상적으로 통신이 성공된 경우
                        Log.d("post", "onResponse 성공: " + response.body().toString());
                        //Toast.makeText(this@ProfileActivity, "비밀번호 찾기 성공!", Toast.LENGTH_SHORT).show()

                    } else {
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d("post", "onResponse 실패"+response.code())
                    }
                }

                override fun onFailure(call: Call<GetTrainerInfoResponse>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d("post", "onFailure 에러: " + t.message.toString());
                }
            })
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}