package com.grammarkadrama.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grammarkadrama.DataHandler.Variables
import com.grammarkadrama.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

//        Log.d("TAG", "onCreate Result Check: ${Variables.questionsList[0].getCorrectOption() ==
//        Variables.questionsList[0].getUserOption()}")

    }
}