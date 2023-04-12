package com.example.music_player.data.repository

import androidx.lifecycle.LiveData
import com.example.music_player.data.api.SignupRequest
import com.example.music_player.data.api.SignupResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    suspend fun signup (
        name : String,
        email : String,
        password : String,
        address : String?,
        phone : String?,
        dateOfBirth : String?
    )

    suspend fun login (email : String, password: String)

}