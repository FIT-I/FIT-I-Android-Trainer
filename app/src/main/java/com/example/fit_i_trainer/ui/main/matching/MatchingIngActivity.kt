package com.example.fit_i_trainer.ui.main.matching

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.GetMatchlistResponse
import com.example.fit_i_trainer.data.service.MatchingService
import com.example.fit_i_trainer.databinding.ActivityChatIngBinding
import com.example.fit_i_trainer.databinding.ActivityMatchingIngBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchingIngActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchingIngBinding


    fun onBind(data: GetMatchlistResponse.Result?){
        binding.tvMatching.text = data?.name
        binding.tvMatchingPrice.text = data?.pricePerHour
        binding.tvMatchingAllprcie2.text = data?.totalPrice
        binding.tvMatchingStart.text = data?.matchingStart
        binding.tvMatchingEnd.text= data?.matchingFinish
        binding.tvMatchingAlldate.text = data?.matchingPeriod.toString()

        if (data?.pickUpType=="TRAINER_GO"){
            binding.tvMatchingPickup2.text = "트레이너님이 와주세요"
        }else if (data?.pickUpType=="CUSTOMER_GO"){
            binding.tvMatchingPickup2.text = "제가 직접 갈게요"
        }
        binding.tvMatchingPlace2.text = data?.location
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchingIngBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val matchingId = intent.getLongExtra("matchingId",-1)//고객
        val trainerId = intent.getLongExtra("trainerId",-1)//트레이너
        val openchatlink = intent.getLongExtra("openChatLink",-1) // 오픈채팅링크

        val btnaccept = findViewById<View>(R.id.btn_matching_accept)
        val btndecline = findViewById<View>(R.id.btn_matching_decline)
        val matchingService = RetrofitImpl.getApiClient().create(MatchingService::class.java)





        //수락한 경우
        btnaccept.setOnClickListener{
//            val intent = Intent(this, MatchingFragment::class.java)
//            startActivity(intent)
//            finish()

            //오픈채팅방링크 작성하도록 수정
            val dialog = layoutInflater.inflate(R.layout.dialog_openchatlink,null)
            val build = AlertDialog.Builder(this).apply {
                setView(dialog)
            }
            val dialogChat = build.create()
            dialogChat.show()
            Log.d("post ", "dialog success")

            val cancel = dialog.findViewById<Button>(R.id.btn_cancel)
            val go = dialog.findViewById<Button>(R.id.btn_go)

            cancel.setOnClickListener{
                dialogChat.dismiss()
            }
            go.setOnClickListener{
                val intent = Intent(this,OpenChatActivity::class.java)
                startActivity(intent)
                dialogChat.dismiss()
                finish()

            }
            //api 연결

            matchingService.matchingaccepat(matchingId,openchatlink).enqueue(object : Callback<BaseResponse>{
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {if (response.isSuccessful){
                    //정상적으로 통신이 된 경우
                    Log.d("post","매칭 onResponse 성공" + response.body().toString())
                }
                else{
                    //통신에 실패한 경우
                    Log.d("post","매칭 onResponse 실패"+response.body().toString())
                }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    //통신실패(예외적인 경우들)
                    Log.d("post","매칭 onFailure 에러" + t.message.toString())
                }
            }
            )
        }

        btndecline.setOnClickListener{
            //이동

            //api 연결
            matchingService.matchingreject(matchingId).enqueue(object : Callback<BaseResponse>{
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {if (response.isSuccessful){
                    //정상적으로 통신이 된 경우
                    Log.d("post","매칭 onResponse 성공" + response.body().toString())
                    Toast.makeText(this@MatchingIngActivity,"매칭 목록에서 제거",Toast.LENGTH_SHORT).show()
                }
                else{
                    //통신에 실패한 경우
                    Log.d("post","매칭 onResponse 실패")
                }
                }

                override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                    //통신실패(예외적인 경우들)
                    Log.d("post","매칭 onFailure 에러" + t.message.toString())
                }
            })
        }

        matchingService.matchinglist(matchingId).enqueue(object :
            Callback<GetMatchlistResponse>{
            override fun onResponse(
                call: Call<GetMatchlistResponse>,
                response: Response<GetMatchlistResponse>
            ) {if (response.isSuccessful){
                Log.d("post","매칭 명세표 onResponse 성공:"+ response.body().toString());
//                onBind(response.body()!!.result)
                response.body()?.let { onBind(it.result) }
            }else{
                //통신실패
                Log.d("post","매칭 명세표 onResponse 실패")
            }
            }

            override fun onFailure(call: Call<GetMatchlistResponse>, t: Throwable) {
                Log.d("post","onFailure 에러 : "+ t.message.toString());
            }
        })
    }
}