package com.example.alphaquiz

import android.app.Activity
import android.content.Intent
import android.gesture.GestureLibrary
import android.os.Bundle
import android.service.autofill.UserData
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.alphaquiz.Activitys.RecentQuiz
import com.example.alphaquiz.Activitys.ShowAllTestActivity
import com.example.alphaquiz.Dataclass.QuizModel
import com.example.alphaquiz.Dataclass.userData
import com.example.alphaquiz.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //set up recent quiz
        setUpRecentQuiz()

        ///set name and image
        setUpNameAndProfile()

        ///profile image click listner
        binding.profileImage.setOnClickListener {

        }

        binding.javatest.setOnClickListener{
            val name=binding.textView9.text.toString()
            val intent=Intent(this,ShowAllTestActivity::class.java)
            intent.putExtra("id","java")
            intent.putExtra("name",name)
            startActivity(intent)
        }

        binding.ctest.setOnClickListener{
            val name=binding.textView10.text.toString()
            val intent=Intent(this,ShowAllTestActivity::class.java)
            intent.putExtra("id","c-language")
            intent.putExtra("name",name)
            startActivity(intent)
        }


        binding.pythontest.setOnClickListener{
            val name=binding.textView40.text.toString()
            val intent=Intent(this,ShowAllTestActivity::class.java)
            intent.putExtra("id","python")
            intent.putExtra("name",name)
            startActivity(intent)
        }

        binding.htmltest.setOnClickListener{
            val name=binding.textView4a0.text.toString()
            val intent=Intent(this,ShowAllTestActivity::class.java)
            intent.putExtra("id","html")
            intent.putExtra("name",name)
            startActivity(intent)
        }

    }

    private fun setUpNameAndProfile() {
        val ref=FirebaseDatabase.getInstance().reference.child("user").child(Utils.currentUserId())
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    val data=snapshot.getValue(userData::class.java)
                    if (data != null) {
                        binding.name.text=data.name.toString()
                        Glide.with(this@MainActivity).load(data.userImage).placeholder(R.drawable.userr).into(binding.profileImage)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setUpRecentQuiz() {
        val quiz= mutableListOf<QuizModel>()
        val recycler=binding.recentRecycler
        recycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val ref=FirebaseDatabase.getInstance().reference.child("recent").child(Utils.currentUserId())
        val recentAdapter=RecentQuiz(quiz)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    quiz.clear()
                    for (snapshot in snapshot.children){
                        val data=snapshot.getValue(QuizModel::class.java)
                        if (data!=null){
                            quiz.add(data)
                        }
                    }
                }
                recycler.adapter=recentAdapter
                recentAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}