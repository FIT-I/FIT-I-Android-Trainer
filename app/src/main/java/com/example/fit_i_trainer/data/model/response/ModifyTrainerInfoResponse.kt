package com.example.fit_i_trainer.data.model.response

data class ModifyTrainerInfoResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
){
    data class Result(
        val name: String,
        val profile: String,
        val background: String,
        val levelName: String,
        val school: String,
        val grade: Double,
        val cost: Long,
        val intro: String,
        val service: String,
        val reviewDto: List<String>,
        val imageList: List<String>,
        val matchingState: Boolean,
        val category: String
        )
}