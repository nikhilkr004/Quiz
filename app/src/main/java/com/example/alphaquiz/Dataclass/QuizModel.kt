package com.example.alphaquiz.Dataclass

import android.os.Parcel
import android.os.Parcelable

data class QuizModel( val id : String,
                      val title : String,
                      val subtitle : String,
                      val time : String,
                      val questionList : List<QuestionModel>
){
    class ViewHolder {

    }

    constructor() : this("","","","", emptyList())
}

data class QuestionModel(
    val question : String?=null,
    val options : List<String>?=null,
    val correct : String?=null,
){
    constructor():this("", emptyList(),"")
}