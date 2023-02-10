package com.example.fit_i_trainer.data.service

import com.example.fit_i_trainer.data.model.response.GetChatResponse
import retrofit2.Call
import retrofit2.http.GET

interface ChatService {
    @GET("api/chat/entered")
    fun chattrainer() : Call<GetChatResponse>


}