package com.example.fit_i_trainer.data.service

import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.GetMatchlistResponse
import com.example.fit_i_trainer.data.model.response.GettrainerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MatchingService {

    //매칭거절
    @PATCH("api/matching/{matchingIdx}/reject")
    fun matchingreject(@Path ("matchingIdx") matchingIdx : Int) : Call<BaseResponse>
    //매칭수락
    @PATCH("api/matching/{matchingIdx}/accept")
    fun matchingaccepat(@Path("matchingIdx") matchingIdx: Int) : Call<BaseResponse>

    //매칭정보조회
    @GET("api/matching/{matchingIdx}")
    fun matchinglist(@Path("matchingIdx") matchingIdx: Int) : Call<GetMatchlistResponse>
    //트레이너의 매칭목록조회
    @GET("api/matching/trainer")
    fun matchingtrainer() : Call<GettrainerResponse>



}