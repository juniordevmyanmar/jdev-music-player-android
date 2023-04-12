package com.example.music_player.data.api

import kotlinx.serialization.Required
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    @SerialName ("name")
    @Required
    val name : String,

    @SerialName ("email")
    @Required
    val email : String,

    @SerialName ("password")
    @Required
    val password : String,

    @SerialName ("address")
    val address : String ?= null,

    @SerialName ("phone")
    val phone : String ?= null,

    @SerialName ("dateOfBirth")
    val dateOfBirth : String ?= null
)
