package com.matin.noora.core.data.remote

import com.google.gson.annotations.SerializedName
import com.matin.noora.core.domain.model.UserCredential

data class UserAuthNetworkRequest(
    @SerializedName("user_name")
    val userName: String,
    val password: String
)

fun UserCredential.toNetwork() = UserAuthNetworkRequest(userName, password)
