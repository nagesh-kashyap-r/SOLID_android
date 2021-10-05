package com.example.solid_android.exception

import com.google.gson.annotations.SerializedName


data class ServiceError(
        @SerializedName("description") val description: String?
)