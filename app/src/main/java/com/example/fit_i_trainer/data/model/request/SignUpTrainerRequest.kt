package com.example.fit_i_trainer.data.model.request

data class SignUpTrainerRequest(
    val email: String,
    val major: String,
    val name: String,
    val password: String
)