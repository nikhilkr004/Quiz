package com.example.alphaquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.alphaquiz.databinding.ActivityWinnerBinding
import kotlinx.coroutines.launch

class WinnerActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityWinnerBinding.inflate(layoutInflater)
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

        lifecycleScope.launch {
            val name=Utils.getCurrentUserName()
            binding.scoreTitle.text="Congratulations! $name "
        }
        val percentage=intent.getIntExtra("percentage",0)
        val currectAns=intent.getStringExtra("currect")
        val totalQuestions=intent.getIntExtra("total",0)
        val score=intent.getIntExtra("score",0)
        binding.scoreProgressIndicator.progress=percentage
        binding.scoreProgressText.text=percentage.toString()+"%"
        binding.toalQustion.text=totalQuestions.toString()
        binding.correctquestion.text=score.toString()


        binding.textView18.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

    }
}