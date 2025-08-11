package com.matin.noora.core.data

import com.matin.noora.core.data.di.IoDispatcher
import com.matin.noora.core.data.remote.AuthApi
import com.matin.noora.core.data.remote.UserAuthNetworkRequest
import com.matin.noora.core.data.remote.UserAuthNetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val authApi: AuthApi, @IoDispatcher private val ioDispatcher: CoroutineDispatcher) {

    fun signIn(userName: String, password: String): Flow<UserAuthNetworkResponse> = flow {
        val result = authenticate(userName, password, authApi::signIn)
        emit(result)
    }

    fun signUp(userName: String, password: String): Flow<UserAuthNetworkResponse> = flow {
        val result = authenticate(userName, password, authApi::signUp)
        emit(result)
    }

    private suspend fun authenticate(
        userName: String,
        password: String,
        api: suspend (UserAuthNetworkRequest) -> UserAuthNetworkResponse,
    ): UserAuthNetworkResponse = withContext(ioDispatcher)  {
            val requestModel = UserAuthNetworkRequest(
                userName,
                password
            )
          api(requestModel)
    }
}