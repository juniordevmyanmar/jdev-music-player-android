package com.example.music_player.ui.view

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.music_player.R

class MainActivity : AppCompatActivity() {

//    val store = DataStoreManager(this)
//    val tokenText = store.getAccessToken.collect{
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // fragment
        if (savedInstanceState == null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<LoginFragment>(R.id.fragment_container_view)
            }
        }

        val loginBtn = findViewById<Button>(R.id.login_btn)
        val signUpBtn = findViewById<Button>(R.id.signup_btn)

        loginBtn.setOnClickListener{
            signUpBtn.isSelected = false
            loginBtn.isSelected = !signUpBtn.isSelected
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_container_view, LoginFragment())
            }
        }


        signUpBtn.setOnClickListener{
            loginBtn.isSelected = false
            signUpBtn.isSelected= !loginBtn.isSelected
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_container_view, SignUpFragment())
            }
        }



    }
}