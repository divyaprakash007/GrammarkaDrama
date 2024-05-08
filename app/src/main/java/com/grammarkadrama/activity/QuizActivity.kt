package com.grammarkadrama.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.grammarkadrama.DataHandler.Variables
import com.grammarkadrama.R

class QuizActivity : AppCompatActivity() {

    private lateinit var selectedCategory: String
    private lateinit var quizId: String
    private lateinit var databaseRef: DatabaseReference

    private lateinit var questionNumberTV: TextView
    private lateinit var questionTV: TextView
    private lateinit var optionsRG: RadioGroup
    private lateinit var option1RB: RadioButton
    private lateinit var option2RB: RadioButton
    private lateinit var option3RB: RadioButton
    private lateinit var option4RB: RadioButton
    private lateinit var previousButton: Button
    private lateinit var hintButton: Button
    private lateinit var nextButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Initialize views
        questionNumberTV = findViewById(R.id.questionNumberTV)
        questionTV = findViewById(R.id.questionTV)
        optionsRG = findViewById(R.id.optionsRG)
        option1RB = findViewById(R.id.option1RB)
        option2RB = findViewById(R.id.option2RB)
        option3RB = findViewById(R.id.option3RB)
        option4RB = findViewById(R.id.option4RB)
        previousButton = findViewById(R.id.previousButton)
        hintButton = findViewById(R.id.hintButton)
        nextButton = findViewById(R.id.nextButton)



        // Retrieve selected category and quiz ID from intent
        selectedCategory = intent.getStringExtra("selectedItem") ?: ""
        quizId = intent.getStringExtra("quizId") ?: ""

        // Initialize Firebase Realtime Database reference
        databaseRef = FirebaseDatabase.getInstance().reference

        // Retrieve data from Firebase Realtime Database
        fetchDataFromFirebase()

    }

    private fun fetchDataFromFirebase() {
        // Construct Firebase Database reference path for the selected category and quiz ID
        val dataRef = databaseRef.child(selectedCategory).child(quizId).child("questions")

        // Attach ValueEventListener to retrieve data
        dataRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                   Variables.mainDataSnapshot = dataSnapshot

                    setQuizView(dataSnapshot)
//                for (i in 0 until dataSnapshot.childrenCount) {
////                    Log.d("TAG", "onDataChange: ${dataSnapshot.child(""+i).child("question_text").getValue<String>()}")
//
//                }
                //                Log.d("TAG", "onDataChange: ${dataSnapshot.child("0").child("question_text").getValue<String>()}")
            } else {
                    Log.d("FirebaseData", "No data found for the selected category and quiz ID.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseData", "Database error occurred: ${databaseError.message}")
            }
        })
    }

    private fun setQuizView(dataSnapshot: DataSnapshot) {
        var currentQuestionIndex = 0

        updateView(currentQuestionIndex,dataSnapshot)
        previousButton.setOnClickListener{
            currentQuestionIndex -= 1
            nextButton.setText("Next")

            if (currentQuestionIndex == 0){
                previousButton.isEnabled = false
            } else {
                previousButton.isEnabled = true
            }
            updateView(currentQuestionIndex,dataSnapshot)
        }

        nextButton.setOnClickListener{
            if (nextButton.text.equals("Submit")){
                val intent = Intent(this, ResultActivity::class.java)
                startActivity(intent)
            } else {
                currentQuestionIndex += 1
                previousButton.isEnabled = true
                if (currentQuestionIndex == 9){
                    nextButton.setText("Submit")
                }
                updateView(currentQuestionIndex, dataSnapshot)
            }
        }

        hintButton.setOnClickListener{

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Hint")
            alertDialogBuilder.setMessage(""+dataSnapshot.child(currentQuestionIndex.toString()).child("hint").getValue())
            alertDialogBuilder.setPositiveButton("Okay", null)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private fun updateView(currentQuestionIndex: Int, dataSnapshot: DataSnapshot) {
        questionNumberTV.setText("Question Number ${currentQuestionIndex+1} / ${dataSnapshot.childrenCount}")
        questionTV.setText(""+ dataSnapshot.child(""+currentQuestionIndex).child("question_text").getValue())

        option1RB.setText(""+ dataSnapshot.child(""+currentQuestionIndex).child("options").child("1").getValue())
        option2RB.setText(""+ dataSnapshot.child(""+currentQuestionIndex).child("options").child("2").getValue())
        option3RB.setText(""+ dataSnapshot.child(""+currentQuestionIndex).child("options").child("3").getValue())
        option4RB.setText(""+ dataSnapshot.child(""+currentQuestionIndex).child("options").child("4").getValue())

        optionsRG.clearCheck()

        // Check if an option is selected for the current question
        val selectedOption = Variables.selectedOptionsMap[currentQuestionIndex]

        // Iterate over the radio buttons in the RadioGroup
        for (i in 0 until optionsRG.childCount) { // Start from index 0
            val radioButton = optionsRG.getChildAt(i) as? RadioButton
            if (radioButton != null) {
                // Check if the option is selected
                radioButton.isChecked = i + 1 == selectedOption // Adjust for 1-indexing
            }
        }
        optionsRG.setOnCheckedChangeListener(null) // Clear any previous listeners
        optionsRG.setOnCheckedChangeListener { group, checkedId ->
            // Get the checked RadioButton's ID
            val checkedRadioButtonId = group.checkedRadioButtonId

            // Handle the selection of RadioButton
            when (checkedRadioButtonId) {
                R.id.option1RB -> {
                   Variables.selectedOptionsMap[currentQuestionIndex] = 1
                }
                R.id.option2RB -> {
                    Variables.selectedOptionsMap[currentQuestionIndex] = 2
                }
                R.id.option3RB -> {
                    Variables.selectedOptionsMap[currentQuestionIndex] = 3
                }
                R.id.option4RB -> {
                    Variables.selectedOptionsMap[currentQuestionIndex] = 4
                }
            }
        }
    }
}