package com.example.fit_i_trainer.data.service

import com.example.fit_i_trainer.data.model.request.ChangePWRequest
import com.example.fit_i_trainer.data.model.request.LoginRequest
import com.example.fit_i_trainer.data.model.request.LogoutRequest
import com.example.fit_i_trainer.data.model.request.SignUpTrainerRequest
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface AccountsService {
    //트레이너 회원가입
    @POST("api/accounts/trainer")
    fun signUpTrainer(@Body req : SignUpTrainerRequest) : Call<BaseResponse>

    //로그인
    @Headers("content-type: application/json")
    @POST("api/accounts/login")
    fun logIn(@Body loginRequest : LoginRequest) : Call<LoginResponse>

    //로그아웃
    @Headers("content-type: application/json")
    @POST("api/accounts/logout")
    fun logOut(@Body token : LogoutRequest) : Call<BaseResponse>

    //비밀번호변경
    @Headers("content-type: application/json")
    @PATCH("api/accounts/password")
    fun changePW(@Body req : ChangePWRequest) : Call<BaseResponse>

    //계정탈퇴
    @Headers("content-type: application/json")
    @PATCH("api/accounts/close")
    fun close() : Call<BaseResponse>

    //계정 비밀번호 조회
    @Headers("content-type: application/json")
    @GET("api/accounts/password/{email}")
    fun findPW(@Path("email") email: String) :Call<BaseResponse>

    //인증메일전송
    @Headers("content-type: application/json")
    @GET("api/accounts/email/{email}")
    fun sendEmail(@Path("email") email : String) : Call<BaseResponse>
}