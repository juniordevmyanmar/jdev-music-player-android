package com.example.music_player.ui.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.music_player.R
import com.example.music_player.data.repository.AuthRepositoryImpl
import com.example.music_player.data.ui_states.AuthUiState
import com.example.music_player.data.ui_states.SignupUiAction
import com.example.music_player.databinding.FragmentSignUpBinding
import com.example.music_player.ui.viewmodel.MyViewModelFactory
import com.example.music_player.ui.viewmodel.SignupViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SignUpFragment : Fragment() {

    private val authRepositoryImpl = AuthRepositoryImpl()
    private lateinit var viewModel : SignupViewModel

    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        // view model instance
        viewModel = ViewModelProvider(requireActivity(), MyViewModelFactory(authRepositoryImpl,null))
            .get(SignupViewModel::class.java)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var name = binding!!.usernameEditField.text
        var email = binding!!.emailEditField.text
        var password = binding!!.passwordEditField.text
        var address = binding!!.addressEditField.text
        var phone = binding!!.phoneEditField.text
        var dob = binding!!.dobEditField.text


        // current calendar
        val currentDate = Calendar.getInstance()
        val currentYear = currentDate.get(Calendar.YEAR)
        val currentMonth = currentDate.get(Calendar.MONTH)
        val currentDay = currentDate.get(Calendar.DAY_OF_MONTH)

        // define date picker on dob field
        binding!!.dobEditField.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener {_,selectedYear,selectedMonth,selectedDay ->
                // set user selected date
                var selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear,selectedMonth,selectedDay)

                // format date
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val formattedDate = formatter.format(selectedDate.time)

                // set on EditText
                binding!!.dobEditField.setText(formattedDate.toString())
                dob = binding!!.dobEditField.text
                Log.d("SignUp", "date field is ${isDateStringValid(binding!!.dobEditField.text.toString())}")
            }, currentYear, currentMonth, currentDay)
            datePickerDialog.show()
        }

        // listener (data pass from Ui Action)
        view.findViewById<MaterialButton>(R.id.signup_button).setOnClickListener {
            // Check edit field
            checkSignupForm(name.toString(),email.toString(),password.toString(),address.toString(),phone.toString(),dob.toString())

            // send data
            val signupUiAction = SignupUiAction.ClickedSignupButton(
                name.toString(),email.toString(),password.toString(),address.toString(),phone.toString(),dob.toString()
            )
            viewModel.handleSignupUiAction(signupUiAction)
        }

        // get ui state and update UI
        lifecycleScope.launch {
            viewModel.signupUiStateFlow.collect{state ->
                when(state){
                    is AuthUiState.Success -> {
                        Toast.makeText(this@SignUpFragment.context, "Success with ${state.response!!.data.toString()}",Toast.LENGTH_LONG).show()
                    }
                    is AuthUiState.Error -> {
                        Toast.makeText(this@SignUpFragment.context, "Error with ${state.status} and ${state.message}" ,Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        Log.d("Login", "Empty state")
                    }
                }
            }
        }
    }




    private fun checkSignupForm(name: String, email: String, password: String, address: String, phone: String, dob: String) {
        if (TextUtils.isEmpty(name.trim())) {
            binding!!.usernameEditField.error = "Name cannot be empty"
        }else if ( name.trim().length >= 100){
            binding!!.usernameEditField.error = "Name cannot be more than 100 char length"
        }else if (email.trim().isEmpty()) {
            binding!!.emailEditField.error = "Email cannot be empty"
        }else if ( !isEmailValid(email) ) {
            binding!!.emailEditField.error = "Invalid email format"
        }else if (password.trim().isEmpty()) {
            binding!!.passwordEditField.error = "Password cannot be empty"
        }else if ( password.trim().length<=8 || password.trim().length>=30 ) {
            binding!!.passwordEditField.error = "Password must be between 8 to 30 char length"
        }else if (address.trim().length >= 50) {
            binding!!.addressEditField.error = "Address cannot be more than 50 chars length"
        }else if ( phone.trim().isNotEmpty() && !isPhoneValid(phone) ){
            binding!!.phoneEditField.error = "Invalid Phone Number"
        }else if ( dob.toString().isNotEmpty() && !isDateStringValid(dob)){
            binding!!.dobEditField.error = "Invalid Date"
        }else{
            binding!!.usernameEditField.error = null
            binding!!.emailEditField.error = null
            binding!!.passwordEditField.error = null
            binding!!.addressEditField.error = null
            binding!!.phoneEditField.error = null
            binding!!.dobEditField.error = null
        }
    } // end of checkEditField()

    private fun isEmailValid (email:String) : Boolean{
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }


    private fun isPhoneValid (phone: String): Boolean{
        // Regular expression for international phone numbers
        val regex = Regex("^(?:[0-9] ?){6,10}[0-9]$")
        return regex.matches(phone)
    }

    private fun isDateStringValid (date : String) : Boolean {
        val regex = Regex("^\\d{4}-\\d{2}-\\d{2}$")
        return regex.matches(date)
    }

}
