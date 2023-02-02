package com.example.fit_i_trainer.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.request.selectCategoryRequest
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.GetTrainerInfoResponse
import com.example.fit_i_trainer.data.service.TrainerService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileModifyCategoryPickActivity:AppCompatActivity() {
    private lateinit var buttonDone : Button
    private lateinit var checkBtn1 : CheckBox
    private lateinit var checkBtn2 : CheckBox
    private lateinit var checkBtn3 : CheckBox
    private lateinit var checkBtn4 : CheckBox
    private lateinit var checkBtn5 : CheckBox
    lateinit var category : String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_modify_category_pick)
        checkBtn1 = findViewById(R.id.cb1)
        checkBtn2 = findViewById(R.id.cb2)
        checkBtn3 = findViewById(R.id.cb3)
        checkBtn4 = findViewById(R.id.cb4)
        checkBtn5 = findViewById(R.id.cb5)
        buttonDone = findViewById(R.id.btn_done)
        buttonDone.isEnabled = false

        checkBtn1.setOnClickListener{onCheckChange(checkBtn1)}
        checkBtn2.setOnClickListener{onCheckChange(checkBtn2)}
        checkBtn3.setOnClickListener{onCheckChange(checkBtn3)}
        checkBtn4.setOnClickListener{onCheckChange(checkBtn4)}
        checkBtn5.setOnClickListener{onCheckChange(checkBtn5)}
        buttonDone.setOnClickListener{
            val intent = Intent(this,ProfileActivity::class.java)
        }
        //뒤로가기
        val goBack = findViewById<ImageButton>(R.id.ib_back_arrow)
        goBack.setOnClickListener{
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent) //화면전환
            finish()
        }
        buttonDone.setOnClickListener{
            val trainerService = RetrofitImpl.getApiClient().create(TrainerService::class.java)
            trainerService.selectCategory(selectCategoryRequest(category)).enqueue(object :
                Callback<BaseResponse> {
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {
                    if (response.isSuccessful) {
                        // 정상적으로 통신이 성공된 경우
                        Log.d("post", "onResponse 성공: " + response.body().toString());
                        //Toast.makeText(this@ProfileActivity, "비밀번호 찾기 성공!", Toast.LENGTH_SHORT).show()

                    } else {
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d("post", "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d("post", "onFailure 에러: " + t.message.toString());
                }
            })

            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
            finish()

        }



    }
    private fun onCheckChange(compoundButton: CompoundButton) {
        when (compoundButton.id) {
            R.id.cb1 -> {
                if (checkBtn1.isChecked) {
                    category = "pt"
                    checkBtn1.isChecked = true
                    checkBtn2.isChecked = false
                    checkBtn3.isChecked = false
                    checkBtn4.isChecked = false
                    checkBtn5.isChecked = false
                    buttonDone.isEnabled = true
                } else {
                    buttonDone.isEnabled = false
                }
            }
            R.id.cb2 -> {
                if (checkBtn2.isChecked) {
                    category = "diet"
                    checkBtn1.isChecked = false
                    checkBtn2.isChecked = true
                    checkBtn3.isChecked = false
                    checkBtn4.isChecked = false
                    checkBtn5.isChecked = false
                    buttonDone.isEnabled = true
                } else {
                    buttonDone.isEnabled = false
                }
            }
            R.id.cb3 -> {
                if (checkBtn3.isChecked) {
                    category = "food"
                    checkBtn1.isChecked = false
                    checkBtn2.isChecked = false
                    checkBtn3.isChecked = true
                    checkBtn4.isChecked = false
                    checkBtn5.isChecked = false
                    buttonDone.isEnabled = true
                } else {
                    buttonDone.isEnabled = false
                }
            }
            R.id.cb4 -> {
                if (checkBtn4.isChecked) {
                    category = "rehab"
                    checkBtn1.isChecked = false
                    checkBtn2.isChecked = false
                    checkBtn3.isChecked = false
                    checkBtn4.isChecked = true
                    checkBtn5.isChecked = false
                    buttonDone.isEnabled = true
                } else {
                    buttonDone.isEnabled = false
                }
            }
            R.id.cb5 -> {
                if (checkBtn5.isChecked) {
                    category = "friend"
                    checkBtn1.isChecked = false
                    checkBtn2.isChecked = false
                    checkBtn3.isChecked = false
                    checkBtn4.isChecked = false
                    checkBtn5.isChecked = true
                    buttonDone.isEnabled = true
                } else {
                    buttonDone.isEnabled = false
                }
            }
        }
    }
}

