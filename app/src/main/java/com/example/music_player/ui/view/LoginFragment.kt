package com.example.music_player.ui.view

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.music_player.DataStoreManager
import com.example.music_player.data.repository.AuthRepositoryImpl
import com.example.music_player.data.ui_states.LoginUiAction
import com.example.music_player.data.ui_states.LoginUiState
import com.example.music_player.databinding.FragmentLoginBinding
import com.example.music_player.ui.viewmodel.LoginViewModel
import com.example.music_player.ui.viewmodel.MyViewModelFactory
import kotlinx.coroutines.launch



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding

    private val authRepositoryImpl = AuthRepositoryImpl()
    private lateinit var viewModel : LoginViewModel

    // data store manager
    private val dataStoreManager : DataStoreManager by lazy {
        DataStoreManager(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding!!.root
        viewModel = ViewModelProvider(
            requireActivity(),
            MyViewModelFactory(authRepositoryImpl,dataStoreManager)
        ).get(LoginViewModel::class.java)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var email = binding!!.loginEmailEditField.text
        var password = binding!!.loginPasswordEditField.text

        // update ui state
        lifecycleScope.launch {
            viewModel.loginUiStateFlow.collect{state ->
                when(state){
                    is LoginUiState.Success -> {
                        // save to datastore
                        dataStoreManager.getAccessToken.collect{
                            Toast.makeText(this@LoginFragment.context, "token : ${it}", Toast.LENGTH_LONG).show()

                            Log.d("Login", "After storing DataStore: ${it}")
                        }

                    }
                    is LoginUiState.Error -> {
                        Log.d("Login", "Login Error with ${state.status} and ${state.message}")
                        Toast.makeText(this@LoginFragment.context, "Error with ${state.status} and ${state.message}" ,
                            Toast.LENGTH_SHORT).show()
                    }
                    is LoginUiState.Loading -> {
                        // progress bar
                    }
                    else -> {
                        Log.d("Login", "Empty State")
                    }
                }
            }
        }

        binding!!.loginButton.setOnClickListener {
            Toast.makeText(this.requireContext(), "clicked", Toast.LENGTH_SHORT).show()
            // check validation
            checkLoginForm(email.toString(),password.toString())
            // ui action
            viewModel.handleLoginUiAction (LoginUiAction.ClickedLoginButton(email.toString(),password.toString()))
        }


    }

    private fun checkLoginForm (email: String, password : String){
        if (email.toString().trim().isEmpty()) {
            binding!!.loginEmailEditField.error = "Email cannot be empty"
        }else if ( !isEmailValid(email.toString()) ) {
            binding!!.loginEmailEditField.error = "Invalid email format"
        }else if (password.toString().trim().isEmpty()) {
            binding!!.loginPasswordEditField.error = "Password cannot be empty"
        }else if (password.toString().trim().length<=8 || password.toString().trim().length>=30) {
            binding!!.loginPasswordEditField.error = "Password must be between 8 to 30 char length"
        }else {
            binding!!.loginEmailEditField.error = null
            binding!!.loginPasswordEditField.error = null
        }
    }

    private fun isEmailValid (email:String) : Boolean{
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }


}