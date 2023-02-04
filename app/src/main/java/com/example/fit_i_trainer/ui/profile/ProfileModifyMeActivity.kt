package com.example.fit_i_trainer.ui.profile

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
import com.example.fit_i_trainer.data.model.response.ModifyTrainerInfoResponse
import com.example.fit_i_trainer.data.service.TrainerService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileModifyMeActivity :AppCompatActivity(){
    var intro : String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_modify_me)
        val name: String = intent.getStringExtra("name").toString()
        val serviceDetail: String = intent.getStringExtra("serviceDetail").toString()
        val cost : Int = intent.getIntExtra("cost",0).toInt()
        //객체생성
        val edit : EditText = findViewById(R.id.tv_about_me_context)
        val done = findViewById<Button>(R.id.btn_done)
        val countText = findViewById<TextView>(R.id.tv_char_num)

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
            }

            override fun afterTextChanged(p0: Editable?) {
                intro = edit.text.toString()
                done.isEnabled = true

            }
        })

        //완료버튼
        done.setOnClickListener{
           //401번오류가 난다,,
            // 토큰 인증이 필요하지만 안 한 경우 / (인증 잘 함 or 인증 필요X 상황에서) request 형식이 옳지 않은데 서버 내 validation 코드에서 잡아내지 못한 경우 => 이건 서버 쪽에 연락주시면 validation 코드 추가하겠습니다..!
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
//            val intent1 = Intent(this,ProfileAboutMeActivity::class.java)
//            intent1.putExtra("contextMe",edit.text.toString())
//            startActivity(intent1)
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