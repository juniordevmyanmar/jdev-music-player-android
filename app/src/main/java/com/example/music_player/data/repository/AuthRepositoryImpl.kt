package com.example.music_player.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.example.music_player.DataStoreManager
import com.example.music_player.data.api.LoginRequest
import com.example.music_player.data.api.LoginResponse
import com.example.music_player.data.api.SignupRequest
import com.example.music_player.data.api.SignupResponse
import com.example.music_player.data.ui_states.AuthUiState
import com.example.music_player.data.ui_states.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.prefs.Preferences

/*
- handle sign up api call
- update current state of UI
 */
class AuthRepositoryImpl : AuthRepository {

    private val apiServices : ApiServices = ApiClient.getApiService()


    // ui state flow
    private val _authUiStateFlow = MutableStateFlow<AuthUiState>(AuthUiState.Empty)
    val authUiStateFlow : StateFlow<AuthUiState> = _authUiStateFlow.asStateFlow()

    private val _loginUiStateFlow = MutableStateFlow<LoginUiState>(LoginUiState.Empty)
    val loginUiStateFlow : StateFlow<LoginUiState> = _loginUiStateFlow.asStateFlow()

    override suspend fun signup(
        name : String,
        email : String,
        password : String,
        address : String?,
        phone : String?,
        dateOfBirth : String?
    ) {
        // handle api
        apiServices.singup(SignupRequest(name,email,password,address,phone,dateOfBirth))
            .enqueue(object : Callback<SignupResponse>{
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {


                    if (response.isSuccessful){
                        response?.let {
                            // update ui state
                            _authUiStateFlow.value = AuthUiState.Success(response.code(),SignupResponse(response.body()!!.data.toString()))
                            Log.d("SignUp", "Successful Signup with ${response.code()} and ${response.body()!!.data}")

                        }
                    }else{
                        _authUiStateFlow.value = AuthUiState.Error(response.code(), response.errorBody()?.string().toString())
                        Log.d("SignUp", "Signup failed with code:${response.code()} " +
                                " error body:${response.errorBody()?.string()}" +
                                " body:${response.raw().toString()}"
                        )
                    }

                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    _authUiStateFlow.value = AuthUiState.Error (null ,t.message.toString())
                    Log.d("SignUp", "Api Call failed with  ${t.stackTrace}")
                }

            })
    }


    override suspend fun login(email : String,password: String){
        apiServices.login(LoginRequest(email,password))
            .enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    try{
                        val loginResponse : LoginResponse? = response.body()
                        if(response.isSuccessful && loginResponse != null ){
                            _loginUiStateFlow.value = LoginUiState.Success(
                                response.code(), loginResponse
                            )
                            Log.d("Login", "Repository: Successful Login  " +
                                    "with custom response class's : ${loginResponse?.data?.token}")

                        }else{
                            _loginUiStateFlow.value = LoginUiState.Error(response.code(),response.errorBody()?.string().toString())
                            Log.d("Login", "Login Error with ${response.code()} and ${response.errorBody()?.string()}")
                        }
                    }catch(e:Exception){
                        _loginUiStateFlow.value = LoginUiState.Error ( response.code(),e.message ?: "Error occured!")
                    }


                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _loginUiStateFlow.value = LoginUiState.Error (null ,t.message.toString())
                    Log.d("Login", "Login Api Call failed with  ${t.stackTrace}")
                }

            })
    }
}