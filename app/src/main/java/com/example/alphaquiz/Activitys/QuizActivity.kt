package com.example.alphaquiz.Activitys

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.alphaquiz.Dataclass.QuestionModel
import com.example.alphaquiz.Dataclass.QuizModel
import com.example.alphaquiz.R
import com.example.alphaquiz.Utils
import com.example.alphaquiz.WinnerActivity
import com.example.alphaquiz.databinding.ActivityQuizBinding
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList

class QuizActivity : AppCompatActivity(), View.OnClickListener {
private val binding by lazy {
    ActivityQuizBinding.inflate(layoutInflater)
}
    companion object {
        var questionid: String=""
        var subtitle: String=""
        var titlee: String=""
        var questionModelList : List<QuestionModel> = listOf()
        var time : String = ""
    }
    var currentQuestionIndex = 0;
    var selectedAnswer = ""
    var score = 0;
    var currect=0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startTimer()
        binding.apply {
            btn0.setOnClickListener(this@QuizActivity)
            btn1.setOnClickListener(this@QuizActivity)
            btn2.setOnClickListener(this@QuizActivity)

            nextBtn.setOnClickListener(this@QuizActivity)
        }

        loadQuestions()
        startTimer()
    }
    private fun startTimer(){
        val totalTimeInMillis = time.toInt() * 60 * 1000L
        object : CountDownTimer(totalTimeInMillis,1000L){
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished /1000
                val minutes = seconds/60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text = String.format("%02d:%02d", minutes,remainingSeconds)

            }

            override fun onFinish() {
                finishQuiz()
            }

        }.start()
    }


    private fun loadQuestions(){
        selectedAnswer = ""
        if(currentQuestionIndex == questionModelList.size){
            finishQuiz()
            return
        }

        binding.apply {
            questionIndicatorTextview.text = "Question ${currentQuestionIndex+1}/ ${questionModelList.size} "
            questionProgressIndicator.progress =
                ( currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100 ).toInt()
            questionTextview.text = questionModelList[currentQuestionIndex].question
            btn0.text = questionModelList[currentQuestionIndex].options!![0]
            btn1.text = questionModelList[currentQuestionIndex].options!![1]
            btn2.text = questionModelList[currentQuestionIndex].options!![2]
//            btn3.text = questionModelList[currentQuestionIndex].options!![3]
        }
    }

    private fun finishQuiz() {
        val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat() ) *100 ).toInt()
        val intent=Intent(this,WinnerActivity::class.java)
        intent.putExtra("percentage",percentage)
        intent.putExtra("total",totalQuestions)
        intent.putExtra("score",score)
        intent.putExtra("currect",currect)
        startActivity(intent)
        val model=QuizModel(questionid,titlee, subtitle, time, questionModelList)
        //// add recent quiz
        val ref=FirebaseDatabase.getInstance().reference.child("recent").child(Utils.currentUserId()).push()
        ref.setValue(model)

        finish()
    }

    override fun onClick(view: View?) {

        binding.apply {
            btn0.setBackgroundColor(getColor(R.color.gray))
            btn1.setBackgroundColor(getColor(R.color.gray))
            btn2.setBackgroundColor(getColor(R.color.gray))

        }

        val clickedBtn = view as TextView
        if(clickedBtn.id==R.id.next_btn){
            //next button is clicked
            if(selectedAnswer.isEmpty()){
                Toast.makeText(applicationContext,"Please select answer to continue",Toast.LENGTH_SHORT).show()
                return;
            }
            if(selectedAnswer == questionModelList[currentQuestionIndex].correct){
                score++
                currect++
                Log.i("Score of quiz",score.toString())
            }
            currentQuestionIndex++
            loadQuestions()
        }else{
            //options button is clicked
            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.lighblue))
        }
    }


}