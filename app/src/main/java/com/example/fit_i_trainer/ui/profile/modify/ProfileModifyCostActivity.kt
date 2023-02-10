package com.example.fit_i_trainer.ui.profile.modify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.request.ModifyTrainerInfoRequest
import com.example.fit_i_trainer.data.model.response.GetTrainerInfoResponse
import com.example.fit_i_trainer.data.model.response.ModifyTrainerInfoResponse
import com.example.fit_i_trainer.data.service.TrainerService
import com.example.fit_i_trainer.databinding.ActivityProfileModifyCostBinding
import com.example.fit_i_trainer.databinding.ActivityProfileModifyMeBinding
import com.example.fit_i_trainer.ui.profile.ProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileModifyCostActivity:AppCompatActivity() {
    private lateinit var binding: ActivityProfileModifyCostBinding

    private lateinit var buttonDone : Button
    private lateinit var checkBtn1 : CheckBox
    private lateinit var checkBtn2 : CheckBox
    private lateinit var checkBtn3 : CheckBox
    private lateinit var checkBtn4 : CheckBox
    private lateinit var checkBtn5 : CheckBox
    private lateinit var checkBtn6 : CheckBox
    private lateinit var etEtc : EditText

    lateinit var cost : String
    lateinit var etc : String


    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityProfileModifyCostBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var data = intent.getParcelableExtra<ModifyTrainerInfoRequest>("modify")
        var costHour: String? = data?.costHour
        var intro: String? = data?.intro
        var name: String? = data?.name
        var serviceDetail: String? = data?.serviceDetail

        checkBtn1 = findViewById(R.id.cb1)
        checkBtn2 = findViewById(R.id.cb2)
        checkBtn3 = findViewById(R.id.cb3)
        checkBtn4 = findViewById(R.id.cb4)
        checkBtn5 = findViewById(R.id.cb5)
        checkBtn6 = findViewById(R.id.cb6)
        etEtc = findViewById(R.id.et_new_price)
        buttonDone = findViewById(R.id.btn_done)
        buttonDone.isEnabled = false
        checkBtn1.setOnClickListener{onCheckChange(checkBtn1)}
        checkBtn2.setOnClickListener{onCheckChange(checkBtn2)}
        checkBtn3.setOnClickListener{onCheckChange(checkBtn3)}
        checkBtn4.setOnClickListener{onCheckChange(checkBtn4)}
        checkBtn5.setOnClickListener{onCheckChange(checkBtn5)}
        checkBtn6.setOnClickListener{onCheckChange(checkBtn6)}
        //뒤로가기
        val goBack = findViewById<ImageButton>(R.id.ib_back_arrow)
        goBack.setOnClickListener{
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent) //화면전환
            finish()
        }

        buttonDone.setOnClickListener{
            val trainerService = RetrofitImpl.getApiClient().create(TrainerService::class.java)
            trainerService.modifyTrainerInfo(ModifyTrainerInfoRequest(cost,intro
                ,name,serviceDetail))
                .enqueue(object : Callback<GetTrainerInfoResponse> {
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
    private fun onCheckChange(compoundButton: CompoundButton){
        when(compoundButton.id){
            R.id.cb1 ->{
                if(checkBtn1.isChecked) {
                    cost = "₩0원"
                    //cost = 0
                    checkBtn1.isChecked = true
                    checkBtn2.isChecked = false
                    checkBtn3.isChecked = false
                    checkBtn4.isChecked = false
                    checkBtn5.isChecked = false
                    checkBtn6.isChecked = false
                    buttonDone.isEnabled = true
                }
                else{
                    buttonDone.isEnabled = false
                }
            }
            R.id.cb2 ->{
                if(checkBtn2.isChecked) {
                    cost = "₩10,000원"
                    //cost = 10000
                    checkBtn1.isChecked = false
                    checkBtn2.isChecked = true
                    checkBtn3.isChecked = false
                    checkBtn4.isChecked = false
                    checkBtn5.isChecked = false
                    checkBtn6.isChecked = false
                    buttonDone.isEnabled = true
                }
                else{
                    buttonDone.isEnabled = false
                }
            }
            R.id.cb3 ->{
                if(checkBtn3.isChecked) {
                    cost = "₩15,000원"
                    //cost = 15000
                    checkBtn1.isChecked = false
                    checkBtn2.isChecked = false
                    checkBtn3.isChecked = true
                    checkBtn4.isChecked = false
                    checkBtn5.isChecked = false
                    checkBtn6.isChecked = false
                    buttonDone.isEnabled = true
                }
                else{
                    buttonDone.isEnabled = false
                }
            }
            R.id.cb4 ->{
                if(checkBtn4.isChecked) {
                    cost = "₩20,000원"
                    //cost = 20000
                    checkBtn1.isChecked = false
                    checkBtn2.isChecked = false
                    checkBtn3.isChecked = false
                    checkBtn4.isChecked = true
                    checkBtn5.isChecked = false
                    checkBtn6.isChecked = false
                    buttonDone.isEnabled = true
                }
                else{
                    buttonDone.isEnabled = false
                }
            }
            R.id.cb5 ->{
                if(checkBtn5.isChecked) {
                    cost = "₩25,000원"
                    //cost = 25000
                    checkBtn1.isChecked = false
                    checkBtn2.isChecked = false
                    checkBtn3.isChecked = false
                    checkBtn4.isChecked = false
                    checkBtn5.isChecked = true
                    checkBtn6.isChecked = false
                    buttonDone.isEnabled = true
                }
                else{
                    buttonDone.isEnabled = false
                }
            }
            R.id.cb6 ->{
                if(checkBtn6.isChecked) {
                    etc = etEtc.text.toString()
                    cost = etc.toString()

                    checkBtn1.isChecked = false
                    checkBtn2.isChecked = false
                    checkBtn3.isChecked = false
                    checkBtn4.isChecked = false
                    checkBtn5.isChecked = false
                    checkBtn6.isChecked = true
                    buttonDone.isEnabled = true
                }
                else{
                    buttonDone.isEnabled = false
                }
            }
        }
    }
}