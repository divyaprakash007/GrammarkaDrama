package com.grammarkadrama.DataHandler

import com.google.firebase.database.DataSnapshot
import com.grammarkadrama.Model.QuizQuestionsModel

object Variables {
    val selectedOptionsMap = mutableMapOf<Int, Int>()
//    lateinit var mainDataSnapshot: DataSnapshot
    val questionsList = mutableListOf<QuizQuestionsModel>()
}