package com.example.juschat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }
    private val database by lazy {
        FirebaseFirestore.getInstance()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        profileSettings.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java));
        }
    }
    fun toAes(v:View) {
        database.collection("users").document(auth.uid!!).update("deviceToken", "Double Rachet")
    }
    fun toRsa(v:View) {
        database.collection("users").document(auth.uid!!).update("deviceToken", "Round5 with Falcon")
    }

    override fun onBackPressed() {
        startActivity(
            Intent(this, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }
}