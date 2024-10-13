package com.example.alphaquiz.Activitys

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alphaquiz.Dataclass.QuizModel
import com.example.alphaquiz.databinding.RecentItemLayoutBinding

class RecentQuiz(val data:List<QuizModel>): RecyclerView.Adapter<RecentQuiz.ViewHolder>() {
    class ViewHolder(val binding:RecentItemLayoutBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data: QuizModel) {
            val context=binding.root.context
            binding.textView16.text=data.title
            binding.textView17.text=data.subtitle

            binding.maincardview.setOnClickListener {
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
        val inflater=LayoutInflater.from(parent.context)
        val binding=RecentItemLayoutBinding.inflate(inflater)
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