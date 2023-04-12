package com.example.music_player.data.repository

import androidx.datastore.preferences.protobuf.Api
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// get ApiService instance
object ApiClient {

    private const val BASE_URL = "http://100.78.73.85:5000"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient())
        .build()

    fun getApiService() : ApiServices {
        val apiServices by lazy {
            retrofit.create(ApiServices::class.java)
        }
        return apiServices
    }
}