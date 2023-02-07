package com.example.fit_i_trainer.data.service

import com.example.fit_i_trainer.data.model.request.ModifyTrainerCategoryRequest
import com.example.fit_i_trainer.data.model.request.ModifyTrainerInfoRequest
import com.example.fit_i_trainer.data.model.request.SelectCategoryRequest
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.GetTrainerHomeResponse
import com.example.fit_i_trainer.data.model.response.GetTrainerInfoResponse
import retrofit2.Call
import retrofit2.http.*

interface TrainerService {

    //트레이너 홈화면
    @GET("api/trainer/home")
    fun getTrainerHome() :Call<GetTrainerHomeResponse>

    //트레이너 개인정보조회
    @GET("api/trainer/information")
    fun getTrainerInfo(): Call<GetTrainerInfoResponse>

    //트레이너 정보수정
    @PUT("api/trainer/information")
    fun modifyTrainerInfo(@Body req : ModifyTrainerInfoRequest) : Call<GetTrainerInfoResponse>

    //트레이너 사진 및 자격증 추가
    //@Headers("content-type: application/json")
    //@POST("api/trainer/etcimg")
    //fun addTrainerEtcImg(@Body ectImage : Array<String>) : Call<BaseResponse>

    //트레이너 프로필 삭제
    @DELETE("api/trainer/profile")
    fun deleteTrainerProflie() : Call<BaseResponse>

    //트레이너 프로필수정
    @PATCH("api/trainer/profile")
    fun modifyTrainerProfile(@Body profileImage : String) : Call<BaseResponse>

    //내 매칭 관리 on/off
    @PATCH ("api/trainer/mymatching")
    fun controlMatchingOnOff() :Call<BaseResponse>

    //트레이너 카테고리 수정
    @PATCH("api/trainer/category")
    fun modifyTrainerCategory(@Body req : ModifyTrainerCategoryRequest) : Call<BaseResponse>

    //트레이너 배경화면수정
    @PATCH ("api/trainer/bgimg")
    fun modifyTrainerBgImg(@Body backgroundImage : String) : Call<BaseResponse>

    //트레이너 사진 및 자격증 삭제
    @DELETE("api/trainer/etcimg/{etcImgIdx}")
    fun deleteTrainerEtcImg(@Path("etcImgIdx") etcImgIdx : Int) :Call<BaseResponse>

    //트레이너 카테고리 수정
    @PATCH ("api/trainer/category")
    fun selectCategory(@Body req : SelectCategoryRequest) : Call<BaseResponse>

}