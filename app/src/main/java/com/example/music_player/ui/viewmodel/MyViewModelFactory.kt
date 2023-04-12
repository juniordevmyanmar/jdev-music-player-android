package com.example.music_player.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.music_player.DataStoreManager
import com.example.music_player.data.repository.AuthRepositoryImpl

class MyViewModelFactory(
    private val authRepositoryImpl: AuthRepositoryImpl,
    private val dataStoreManager: DataStoreManager?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when(modelClass){
        SignupViewModel::class.java -> SignupViewModel(authRepositoryImpl)
        LoginViewModel::class.java -> dataStoreManager?.let {
            LoginViewModel(authRepositoryImpl,
                it
            )
        }
        else -> throw java.lang.IllegalArgumentException("unknown ViewModel class")
    } as T

//        when(modelClass){
//            is modelClass.isAssignableFrom(SignupViewModel::class.java)
//        }
//        if (modelClass.isAssignableFrom(SignupViewModel::class.java) ){
//            return SignupViewModel(authRepositoryImpl) as T
//        }
//        throw java.lang.IllegalArgumentException("unknown ViewModel class")
}