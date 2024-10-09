package com.example.alphaquiz.Activitys

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alphaquiz.MainActivity
import com.example.alphaquiz.R
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

Handler(Looper.getMainLooper()).postDelayed({
    if (FirebaseAuth.getInstance().currentUser!=null) {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
    else{
        startActivity(Intent(this,SignUpActivity::class.java))
        finish()
    }
},100)
    }
}