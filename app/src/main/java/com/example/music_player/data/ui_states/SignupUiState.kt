package com.example.music_player.data.ui_states

import com.example.music_player.data.api.SignupResponse

//Ui state
sealed class AuthUiState{
//    object Loading : AuthUiState()
    data class Success (val status: Int, val response : SignupResponse) : AuthUiState()
    data class Error (val status : Int?, val message : String) : AuthUiState()
    object Empty : AuthUiState()
}

// ui action
sealed class SignupUiAction {
    data class ClickedSignupButton (val name: String, val email: String, val password : String, val address : String?, val phone : String?, val dateOfBirth:String?) : SignupUiAction()
}


