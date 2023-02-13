package com.example.fit_i_trainer.ui.main.chat

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.GetChatResponse
import com.example.fit_i_trainer.data.model.response.GetMatchlistResponse
import com.example.fit_i_trainer.data.service.MatchingService
import com.example.fit_i_trainer.databinding.ActivityChatIngBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChatIngActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatIngBinding

    fun onBind(data: GetMatchlistResponse.Result){
        binding.tvCustomerName.text = data.name
        binding.tvChatPrice.text = data.pricePerHour
        binding.tvChatAllprcie2.text =data.totalPrice
        binding.tvChatStart.text = data.matchingStart
        binding.tvChatEnd.text = data.matchingFinish
        binding.tvChatAlldate.text = data.matchingPeriod.toString()
        if(data.pickUpType == "TRAINER_GO"){
            binding.tvChatPickup2.text = "트레이너님이 와주세요"
            binding.tvChatPlace2.text = data.location

        }else if (data.pickUpType == "CUSTOMER_GO"){
            binding.tvChatPickup2.text = "제가 직접 갈게요"

        }


    }

    fun bind(data: GetMatchlistResponse.Result){
        binding.tvCustomerName2.text = data.name
        binding.tvChatPriceIv.text = data.pricePerHour
        binding.tvChatAllprcie2Iv.text =data.totalPrice
        binding.tvChatStartIv.text = data.matchingStart
        binding.tvChatEndIv.text = data.matchingFinish
        binding.tvChatAlldateIv.text = data.matchingPeriod.toString()
         if (data.pickUpType == "CUSTOMER_GO"){
            binding.tvChatPickup2.text = "제가 직접 갈게요"
        }
        }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityChatIngBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val matchingId = intent.getLongExtra("matchingId",-1)
        val chatService = RetrofitImpl.getApiClient().create(MatchingService::class.java)

        chatService.matchinglist(matchingId).enqueue(object :
            Callback<GetMatchlistResponse> {
            override fun onResponse(
                call: Call<GetMatchlistResponse>,
                response: Response<GetMatchlistResponse>
            ) {if (response.isSuccessful){
                Log.d("post","매칭 명세표 onResponse 성공:"+ response.body().toString());

                if (response.body()?.result?.pickUpType == "TRAINER_GO") {
                    val body = response.body()
                    body?.let { onBind(it.result) }
                }else if (response.body()?.result?.pickUpType == "CUSTOMER_GO"){
                    val body = response.body()
                    body?.let { bind(it.result) }
                }
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
