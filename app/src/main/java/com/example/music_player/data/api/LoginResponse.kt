package com.example.music_player.data.api

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class LoginResponse(
    val data : Data
){
    data class Data(
//        @SerialName("token")
        val token : String?
    )
}
