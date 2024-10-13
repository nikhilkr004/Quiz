package com.example.alphaquiz.Activitys

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alphaquiz.Dataclass.QuizModel
import com.example.alphaquiz.R
import com.example.alphaquiz.databinding.ActivityShowAllTestBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShowAllTestActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityShowAllTestBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id=intent.getStringExtra("id").toString()
        val name =intent.getStringExtra("name").toString()

        binding.imageView8.setOnClickListener {
            finish()
        }

        binding.textView12.text=name

        val data= mutableListOf<QuizModel>()

        val recyclerview=binding.recyclerview
        recyclerview.layoutManager=LinearLayoutManager(this)
        val adapters = QuizAdapter(data)
        val ref=FirebaseDatabase.getInstance().reference.child(id)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot in snapshot.children) {
                    val model = snapshot.getValue(QuizModel::class.java)
                    if (model != null) {
                        data.add(model)

                        binding.recyclerview.visibility=View.VISIBLE
                        binding.shimmer.visibility=View.GONE
                    }
                }

                recyclerview.adapter = adapters
                adapters.notifyDataSetChanged()
            }



            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}