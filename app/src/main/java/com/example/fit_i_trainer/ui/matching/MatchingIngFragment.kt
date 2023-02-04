package com.example.fit_i_trainer.ui.matching

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.fit_i_trainer.R
import com.example.fit_i_trainer.RetrofitImpl
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.service.MatchingService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MatchingIngFragment : Fragment() {
    private var matchingIdx : Int = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_matching_ing,container,false)
        val btnaccept = view.findViewById<View>(R.id.btn_matching_accept)
        val btndecline = view.findViewById<View>(R.id.btn_matching_decline)
        val matchingService = RetrofitImpl.getApiClient().create(MatchingService::class.java)


        btnaccept.setOnClickListener{
            val matchingFragment = MatchingFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container,matchingFragment)
            transaction.commit()


            //api 연결

            matchingService.matchingaccepat(matchingIdx).enqueue(object : Callback<BaseResponse>{
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
            val matchingFragment = MatchingFragment()
            val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()

            transaction.replace(R.id.fl_container,matchingFragment)
            transaction.commit()

            //api 연결
            matchingService.matchingreject(matchingIdx).enqueue(object : Callback<BaseResponse>{
                override fun onResponse(
                    call: Call<BaseResponse>,
                    response: Response<BaseResponse>
                ) {if (response.isSuccessful){
                    //정상적으로 통신이 된 경우
                    Log.d("post","매칭 onResponse 성공" + response.body().toString())
                    Toast.makeText(getActivity(),"매칭 목록에서 제거",Toast.LENGTH_SHORT).show()
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

        return view

    }


}