package com.example.alphaquiz.Activitys

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.alphaquiz.Dataclass.QuestionModel
import com.example.alphaquiz.Dataclass.QuizModel
import com.example.alphaquiz.databinding.CategoryItemBinding

import kotlin.collections.ArrayList


class QuizAdapter(val data:List<QuizModel>):RecyclerView.Adapter<QuizAdapter.ViewHolder>() {
    class ViewHolder(val binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data: QuizModel) {
            val context=binding.root.context
            binding.quizTitleText.text=data.title.toString()
            binding.quizTimeText.text=data.time.toString()
            binding.quizSubtitleText.text=data.subtitle.toString()

            binding.relaiveLayout.setOnClickListener {
                val intent  = Intent(context,QuizActivity::class.java)
                QuizActivity.questionModelList = data.questionList
                QuizActivity.time = data.time
                QuizActivity.titlee=data.title
                QuizActivity.subtitle=data.subtitle
                QuizActivity.questionid=data.id
                context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate=LayoutInflater.from(parent.context)
        val binding=CategoryItemBinding.inflate(inflate)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val data=data[position]
        holder.bind(data)
    }
}