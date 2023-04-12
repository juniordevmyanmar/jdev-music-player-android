package com.example.music_player.ui.viewmodel

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.music_player.DataStoreManager
import com.example.music_player.data.api.LoginResponse
import com.example.music_player.data.repository.AuthRepositoryImpl
import com.example.music_player.data.ui_states.AuthUiState
import com.example.music_player.data.ui_states.LoginUiAction
import com.example.music_player.data.ui_states.LoginUiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val dataStoreManager: DataStoreManager
    ) : ViewModel(){

    val loginUiStateFlow : StateFlow<LoginUiState> = authRepositoryImpl.loginUiStateFlow

    fun handleLoginUiAction (loginUiAction: LoginUiAction){
        when(loginUiAction){
            is LoginUiAction.ClickedLoginButton -> {
                viewModelScope.launch {
                    authRepositoryImpl.login(loginUiAction.email, loginUiAction.password)
                    // save token to datastore
                    loginUiStateFlow.collect{state->
                        when(state){
                            is LoginUiState.Success -> {
                                dataStoreManager.saveToken(state.loginResponse!!.data!!.token!!)
                                Log.d("Login", "Saved into DataStore")
                            }
                            else -> {

                            }
                        }
                    }

                }
            }
        }
    }
}