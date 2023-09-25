package com.example.github_user_application.data.remote.retrofit

import com.example.github_user_application.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val loggingInterceptor=
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client=OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val originalRequest: Request = chain.request()
                    val requestBuilder: Request.Builder = originalRequest.newBuilder()
                        .header("Authorization", BuildConfig.Token)
                    val request: Request = requestBuilder.build()
                    chain.proceed(request)

                }
                .build()
            val retrofit=Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}