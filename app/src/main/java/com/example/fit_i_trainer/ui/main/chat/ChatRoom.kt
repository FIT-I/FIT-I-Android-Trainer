package com.example.fit_i_trainer.ui.main.chat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatRoom(
    val openChatLink : String,
    val trainerId : Int,
    val trainerName : String,
    val trainerGrade : Number,
    val trainerSchool : String,
    val customerId : Int,
    val customerName: String,
    val pickUp : String,
    val customerLocation : String,
    val createdAt : String,
    val matchingId : Int,
    val trainerProfile : String

): Parcelable