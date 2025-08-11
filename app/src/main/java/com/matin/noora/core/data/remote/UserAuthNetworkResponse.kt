package com.matin.noora.core.data.remote

import com.google.gson.annotations.SerializedName

data class UserAuthNetworkResponse(
    @SerializedName("user_id")
    val userId: String?,
    val userName: String?,
    val token: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?
)