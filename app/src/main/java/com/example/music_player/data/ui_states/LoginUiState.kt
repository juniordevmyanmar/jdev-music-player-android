package com.example.music_player.data.ui_states

import com.example.music_player.data.api.LoginResponse
import com.example.music_player.data.api.SignupResponse

sealed class LoginUiState{
    data class Success (val status: Int, val loginResponse : LoginResponse) : LoginUiState()
    data class Error (val status : Int?, val message : String) : LoginUiState()
    object Empty : LoginUiState()
    object Loading : LoginUiState()
}

// login action
sealed class LoginUiAction {
    data class ClickedLoginButton (val email: String, val password: String) : LoginUiAction()
}
