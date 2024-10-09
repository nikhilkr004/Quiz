package com.example.alphaquiz.Activitys

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alphaquiz.Dataclass.userData
import com.example.alphaquiz.MainActivity
import com.example.alphaquiz.R
import com.example.alphaquiz.Utils
import com.example.alphaquiz.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
   private lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth=FirebaseAuth.getInstance()

        binding.signUpbtn.setOnClickListener{
            signUpWithUser()
        }

        ///check user already register
        checkUserRegister()

    }

    private fun checkUserRegister() {

    }

    private fun signUpWithUser() {
        val name=binding.name.text.toString()
        val email=binding.email.text.toString()
        val password=binding.password.text.toString()

        if (name.equals("")){
            binding.name.setError("Enter name!")
        }
        else if (email.equals("")){
            binding.email.setError("Enter email!")
        }
        else if (password.equals("")){
            binding.password.setError("Enter password")
        }
        else{
            Utils.showDialog(this,"Please wait signing up...")

            firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener {
                    auth->
                    if (auth.isSuccessful){
                        val userdata=userData(
                            name=name,
                            email=email,
                            password=password,
                            userId = Utils.currentUserId()
                        )

                        val ref=FirebaseDatabase.getInstance().reference.child("user").child(Utils.currentUserId())
                        ref.setValue(userdata).addOnCompleteListener {
                            if (it.isSuccessful) {
                                startActivity(Intent(this,MainActivity::class.java))
                                Utils.hideDialog()
                                Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            else{
                                Utils.hideDialog()
                            }
                        }
                    }
                }
        }
    }
}