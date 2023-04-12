package com.example.music_player.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        loginBtn.setOnClickListener{
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_container_view, LoginFragment())
            }
//            Toast.makeText("MainActivity","Login Fragment",Toast.SHORT_LEN)
        }

        val signUpBtn = findViewById<Button>(R.id.signup_btn)
        signUpBtn.setOnClickListener{
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment_container_view, SignUpFragment())
            }
        }


    }
}