package com.example.fit_i_trainer.data.model.request

data class ModifyTrainerInfoRequest(
    val costHour: Int,
    val intro: String,
    val name: String,
    val serviceDetail: String
)