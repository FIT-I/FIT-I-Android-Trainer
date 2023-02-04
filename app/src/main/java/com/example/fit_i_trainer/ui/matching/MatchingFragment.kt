package com.example.fit_i_trainer.ui.matching

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.GetMatchlistResponse
import com.example.fit_i_trainer.data.model.response.GettrainerResponse
import com.example.fit_i_trainer.data.service.MatchingService
import com.example.fit_i_trainer.databinding.FragmentMatchingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchingFragment : Fragment() {
    private var _binding : FragmentMatchingBinding? = null
    private val binding : FragmentMatchingBinding
    get() = requireNotNull(_binding){"FragmentMatchingBinding"}
//    private val dataList = ArrayList<MatchingData>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatchingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()

        val matchTlist : ArrayList<MatchingData> = arrayListOf()


    }

    private fun setAdapter(matchTTlist : List<GettrainerResponse.Result>){
        val matchingAdapter = MatchingAdapter(matchTTlist)
        binding.rcmatching.adapter = matchingAdapter

        val linearLayoutManager = LinearLayoutManager(context)
        binding.rcmatching.layoutManager = linearLayoutManager

        binding.rcmatching.setHasFixedSize(true)

    }

    private fun loadData(){
        val matchingTTService = RetrofitImpl.getApiClient().create(MatchingService::class.java)
        matchingTTService.matchingtrainer().enqueue(object :
        Callback<GettrainerResponse>{
            override fun onResponse(
                call: Call<GettrainerResponse>,
                response: Response<GettrainerResponse>
            ) {
                if (response.isSuccessful){
                    //정상적으로 통신이 성공된 경우
                    Log.d("post","매칭목록 onResponse 성공:" + response.body().toString())
                    val body = response.body()
                    body?.let {
                        setAdapter(it.result)

                    }
                }else{
                    //통신이 실패한 경우(응답 코드 발생 )
                    Log.d("post","매칭목록 onResponse 실패")
                }
            }

            override fun onFailure(call: Call<GettrainerResponse>, t: Throwable) {
               //통신 실패(인터넷 끊김, 예외적 상황 등)
                Log.d("post","onFailure 에러:"+t.message.toString())
            }
        })
    }

}