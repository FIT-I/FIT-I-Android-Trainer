package com.example.fit_i_trainer.data.model.response

data class GetChatResponse(
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : List<Result>
){
    data class Result(
        val openChatLink : String,
        val trainerId : Int,
        val trainerName : String,
        val trainerGrade : Number,
        val trainerSchool : String,
        val customerId : Int,
        val customerName : String,
        val pickUp : String,
        val customerLocation : String,
        val createdAt : String,
        val matchingId : Int,
        val trainerProfile : String,
        val trainerLocation : String,
        val customerProfile : String
    )
}
