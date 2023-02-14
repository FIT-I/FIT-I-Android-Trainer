package com.example.fit_i_trainer.data.service

import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.GetChatResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ChatService {
    @GET("api/chat/entered")
    fun chattrainer() : Call<GetChatResponse>

    @PATCH("api/trainer/chat/{openChatLink}")
    fun openchat(@Path("openChatLink") openChatLink : String) : Call<BaseResponse>


}