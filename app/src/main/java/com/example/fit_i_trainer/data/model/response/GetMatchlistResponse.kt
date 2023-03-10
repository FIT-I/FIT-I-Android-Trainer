package com.example.fit_i_trainer.data.model.response

data class GetMatchlistResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: Result
) {
    data class Result(
        val matchingId: Int,
        val customerId: Int,
        val name: String,
        val pricePerHour: String,
        val totalPrice: String,
        val matchingStart: String,
        val matchingFinish: String,
        val matchingPeriod: Int,
        val pickUpType: String, // 두가지 리스트 있음
        val location: String

    )
}

