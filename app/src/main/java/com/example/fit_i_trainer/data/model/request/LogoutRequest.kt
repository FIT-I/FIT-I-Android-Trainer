package com.example.fit_i_trainer.data.model.request

data class LogoutRequest(
    val accessToken: String,
    val refreshToken: String
)