package com.example.fit_i_trainer.data.model.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModifyTrainerInfoRequest(
    val costHour: String?,
    val intro: String?,
    val name: String?,
    val serviceDetail: String?
): Parcelable