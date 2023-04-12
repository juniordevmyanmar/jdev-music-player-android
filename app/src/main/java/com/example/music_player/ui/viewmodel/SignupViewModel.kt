package com.example.music_player.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music_player.data.repository.AuthRepositoryImpl
import com.example.music_player.data.ui_states.AuthUiState
import com.example.music_player.data.ui_states.SignupUiAction
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignupViewModel (
    private val authRepositoryImpl: AuthRepositoryImpl
        ) : ViewModel(){

    // get ui state flow (to collect as StateFlow)
    val signupUiStateFlow : StateFlow<AuthUiState> = authRepositoryImpl.authUiStateFlow


    fun handleSignupUiAction (signupUiAction: SignupUiAction.ClickedSignupButton){
        viewModelScope.launch {
            authRepositoryImpl.signup(
                signupUiAction.name,
                signupUiAction.email,
                signupUiAction.password,
                signupUiAction.address,
                signupUiAction.phone,
                signupUiAction.dateOfBirth
            )
        }
    }

}