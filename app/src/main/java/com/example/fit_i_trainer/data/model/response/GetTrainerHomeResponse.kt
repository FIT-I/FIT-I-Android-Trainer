package com.example.fit_i_trainer.data.model.response

data class GetTrainerHomeResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
){data class Result(
    val category: String,
    val certificateNum: Int,
    val contents: String,
    val email: String,
    val grade: Double,
    val id: Int,
    val levelName: String,
    val name: String,
    val school: String
)}