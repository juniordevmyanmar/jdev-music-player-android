package com.example.music_player.data.api

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class LoginRequest(
    @SerialName("email")
    @Required
    val email : String,

    @SerialName("password")
    @Required
    val password : String
)
