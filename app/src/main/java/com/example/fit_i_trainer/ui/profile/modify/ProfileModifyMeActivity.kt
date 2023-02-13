package com.example.fit_i_trainer.ui.profile.modify

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.request.ModifyTrainerInfoRequest
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.GetTrainerInfoResponse
import com.example.fit_i_trainer.data.service.TrainerService
import com.example.fit_i_trainer.databinding.ActivityProfileModifyMeBinding
import com.example.fit_i_trainer.databinding.ActivityProfileModifyServiceBinding
import com.example.fit_i_trainer.ui.profile.ProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileModifyMeActivity :AppCompatActivity(){
    private lateinit var binding: ActivityProfileModifyMeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileModifyMeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.getParcelableExtra<ModifyTrainerInfoRequest>("modify")
        var costHour: String? = data?.costHour
        var intro: String? = data?.intro
        var name: String? = data?.name
        var serviceDetail: String? = data?.serviceDetail

        var modiIntro : String=""


        //객체생성
        val edit : EditText = findViewById(R.id.tv_about_me_context)
        val done = findViewById<Button>(R.id.btn_done)
        //val countText = findViewById<TextView>(R.id.tv_char_num)
        edit.setText(intro)
        binding.tvCharNum.text=edit.length().toString()
        done.isEnabled = false

        edit.addTextChangedListener(object:TextWatcher{
            var maxText =""
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                maxText = p0.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if(edit.length()>500){
//                    Toast.makeText(this@ProfileModifyMeActivity,"최대 500자까지 입력 가능합니다.",
//                    Toast.LENGTH_SHORT).show()
//                    edit.setText(maxText)
//                    edit.setSelection(edit.length())
//                    countText.setText("${edit.length()}")
//                }
                modiIntro = edit.text.toString()
            }

            override fun afterTextChanged(p0: Editable?) {
                done.isEnabled = true
            }
        })

        //완료버튼
        done.setOnClickListener{
            val trainerService = RetrofitImpl.getApiClient().create(TrainerService::class.java)
            trainerService.modifyTrainerInfo(
                ModifyTrainerInfoRequest(costHour,modiIntro
                    ,name,serviceDetail)
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