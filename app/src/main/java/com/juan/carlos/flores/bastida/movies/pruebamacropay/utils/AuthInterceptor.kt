package com.juan.carlos.flores.bastida.movies.pruebamacropay.utils

import com.juan.carlos.flores.bastida.movies.pruebamacropay.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(newRequestWithAccessToken(BuildConfig.API_READ_TOKEN, request))
        return response
    }

    private fun newRequestWithAccessToken(accessToken: String?, request: Request): Request =
        request.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
}