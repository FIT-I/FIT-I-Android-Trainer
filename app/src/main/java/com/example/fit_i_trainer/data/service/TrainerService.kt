package com.example.fit_i_trainer.data.service

import com.example.fit_i_trainer.data.model.request.ModifyTrainerCategoryRequest
import com.example.fit_i_trainer.data.model.request.ModifyTrainerInfoRequest
import com.example.fit_i_trainer.data.model.response.BaseResponse
import com.example.fit_i_trainer.data.model.response.GetTrainerInfoResponse
import retrofit2.Call
import retrofit2.http.*

interface TrainerService {

    //트레이너 개인정보조회
    @Headers("content-type: application/json")
    @GET("api/trainer/information")
    fun getTrainerInfo(): retrofit2.Call<GetTrainerInfoResponse>

    //트레이너 정보수정
    @Headers("content-type: application/json")
    @PUT("api/trainer/information")
    fun modifyTrainerInfo(@Body req : ModifyTrainerInfoRequest) : retrofit2.Call<BaseResponse>

    //트레이너 사진 및 자격증 추가
    //@Headers("content-type: application/json")
    //@POST("api/trainer/etcimg")
    //fun addTrainerEtcImg(@Body ectImage : Array<String>) : Call<BaseResponse>

    //트레이너 프로필 삭제
    @Headers("content-type: application/json")
    @DELETE("api/trainer/profile")
    fun deleteTrainerProflie() : retrofit2.Call<BaseResponse>

    //트레이너 프로필수정
    @Headers("content-type: application/json")
    @PATCH("api/trainer/profile")
    fun modifyTrainerProfile(@Body profileImage : String) : retrofit2.Call<BaseResponse>

    //내 매칭 관리 on/off
    @Headers("content-type: application/json")
    @PATCH ("api/trainer/mymatching")
    fun controlMatchingOnOff() : retrofit2.Call<BaseResponse>

    //트레이너 카테고리 수정
    @Headers("content-type: application/json")
    @PATCH("api/trainer/category")
    fun modifyTrainerCategory(@Body req : ModifyTrainerCategoryRequest) : retrofit2.Call<BaseResponse>

    //트레이너 배경화면수정
    @Headers("content-type: application/json")
    @PATCH ("api/trainer/bgimg")
    fun modifyTrainerBgImg(@Body backgroundImage : String) : retrofit2.Call<BaseResponse>

    //트레이너 사진 및 자격증 삭제
    @Headers("content-type: application/json")
    @DELETE("api/trainer/etcimg/{etcImgIdx}")
    fun deleteTrainerEtcImg(@Path("etcImgIdx") etcImgIdx : Int) : retrofit2.Call<BaseResponse>

}