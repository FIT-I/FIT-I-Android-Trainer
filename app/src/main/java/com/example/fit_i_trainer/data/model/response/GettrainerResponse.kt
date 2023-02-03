package com.example.fit_i_trainer.data.model.response

data class GettrainerResponse(
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result : List<Result>
    )
{
    data class Result(
        val matchingId : Int,
        val customerId : Int,
        val name : String,
        val profile : String,
        val pickUpType : String,
        val orderDate: String,
        val orderDateGap : Int
    )
}


