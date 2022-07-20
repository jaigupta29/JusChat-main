package com.example.juschat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent1 = Intent(this,LoginActivity::class.java)
        val intent2 = Intent(this,MainActivity::class.java)

        Timer().schedule(2500){
            // do something after 1 second
            if(auth.currentUser == null){
                startActivity(intent1)
            }else{
                startActivity(intent2)
            }
            finish()
        }

    }
}