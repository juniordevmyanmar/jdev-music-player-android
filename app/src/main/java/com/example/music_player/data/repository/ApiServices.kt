package com.example.music_player.data.repository

import com.example.music_player.data.api.LoginRequest
import com.example.music_player.data.api.LoginResponse
import com.example.music_player.data.api.SignupRequest
import com.example.music_player.data.api.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// api endpoints
interface ApiServices {

    @POST("/register")
    fun singup (
        @Body signupRequest: SignupRequest
    ) : Call<SignupResponse>

    @POST("/login")
    fun login(
        @Body loginRequest: LoginRequest
    ) : Call<LoginResponse>
}