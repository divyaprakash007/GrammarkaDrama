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
import com.grammarkadrama.Model.QuizQuestionsModel
import com.grammarkadrama.R

class QuizActivity : AppCompatActivity() {

    private lateinit var selectedCategory: String
    private lateinit var quizId: String
    private lateinit var databaseRef: DatabaseReference
    private lateinit var questionNumberTV: TextView
    private lateinit var questionTV: TextView
    private lateinit var answerTV: TextView
    private lateinit var explanationTV: TextView
    private lateinit var optionsRG: RadioGroup
    private lateinit var option1RB: RadioButton
    private lateinit var option2RB: RadioButton
    private lateinit var option3RB: RadioButton
    private lateinit var option4RB: RadioButton
    private lateinit var previousButton: Button
    private lateinit var hintButton: Button
    private lateinit var nextButton: Button

    // Declare a global variable for the dialog
    private var progressDialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        Variables.questionsList.clear()
        // Initialize views
        questionNumberTV = findViewById(R.id.questionNumberTV)
        questionTV = findViewById(R.id.questionTV)
        explanationTV = findViewById(R.id.explanationTV)
        answerTV = findViewById(R.id.answerTV)
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
        showProgressDialog()
        // Construct Firebase Database reference path for the selected category and quiz ID
        val dataRef = databaseRef.child(selectedCategory).child(quizId).child("questions")

        // Attach ValueEventListener to retrieve data
        dataRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                hideProgressDialog()
                if (dataSnapshot.exists()) {

                    for (i in 0 until dataSnapshot.childrenCount) {
                        Log.d("TAG","onDataChange: ${dataSnapshot.child("" + i).child("question_text").value}"
                        )

                        val questionModel = QuizQuestionsModel()
                        questionModel.setQuestionText(
                            "" + dataSnapshot.child("" + i).child("question_text").value
                        )

                        val options = mapOf(
                            "A" to "" + dataSnapshot.child("" + i).child("options").child("1").value,
                            "B" to "" + dataSnapshot.child("" + i).child("options").child("2").value,
                            "C" to "" + dataSnapshot.child("" + i).child("options").child("3").value,
                            "D" to "" + dataSnapshot.child("" + i).child("options").child("4").value,
                        )
                        questionModel.setOptions(options)
                        questionModel.setCorrectOption(dataSnapshot.child("" + i).child("correct_option").getValue(Int::class.java) ?: 0)

                        questionModel.setHint(
                            "" + dataSnapshot.child("" + i).child("hint").value
                        )
                        questionModel.setExplanation(
                            "" + dataSnapshot.child("" + i).child("explanation").value
                        )

                        Variables.questionsList.add(questionModel)
                    }



                    setQuizView()
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
                hideProgressDialog()
                Log.e("FirebaseData", "Database error occurred: ${databaseError.message}")
            }
        })
    }

    private fun setQuizView() {
        var currentQuestionIndex = 0

        updateView(currentQuestionIndex)
        previousButton.setOnClickListener {
            currentQuestionIndex -= 1
            nextButton.text = "Next"

            previousButton.isEnabled = currentQuestionIndex != 0
            updateView(currentQuestionIndex)
        }

        nextButton.setOnClickListener {
            if (nextButton.text.equals("Submit")) {
                val intent = Intent(this, ResultActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, 0)
            } else {
                currentQuestionIndex += 1
                previousButton.isEnabled = true
                if (currentQuestionIndex == 9) {
                    nextButton.text = "Submit"
                }
                updateView(currentQuestionIndex)
            }
        }

        hintButton.setOnClickListener {

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Hint")
            alertDialogBuilder.setMessage(""+ Variables.questionsList[currentQuestionIndex].getHint())
            alertDialogBuilder.setPositiveButton("Okay", null)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    // Function to show custom progress dialog
    private fun showProgressDialog() {
        val dialogView = layoutInflater.inflate(R.layout.custom_progress_dialog, null)
        progressDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        progressDialog?.show()
    }

    // Function to hide custom progress dialog
    private fun hideProgressDialog() {
        // Dismiss the dialog if it's currently showing
        progressDialog?.let { dialog ->
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
    }


    private fun updateView(currentQuestionIndex: Int) {
        val optionsMap = Variables.questionsList[currentQuestionIndex].getOptions("A") // Get the options map for the current question

        explanationTV.setText("")
        answerTV.setText("")

        questionNumberTV.text = "Question Number ${currentQuestionIndex + 1} / ${Variables.questionsList.size}"
        questionTV.text = Variables.questionsList[currentQuestionIndex].getQuestionText()

        option1RB.text = optionsMap["A"]
        option2RB.text = optionsMap["B"]
        option3RB.text = optionsMap["C"]
        option4RB.text = optionsMap["D"]

        optionsRG.setOnCheckedChangeListener(null) // Clear any previous listeners

        // Check if an option is selected for the current question
        val selectedOption = Variables.questionsList[currentQuestionIndex].getUserOption()
        Log.d("TAG", "updateView: selected Option: ${selectedOption}")
        // Iterate over the radio buttons in the RadioGroup
        for (i in 0 until optionsRG.childCount) {
            val radioButton = optionsRG.getChildAt(i) as? RadioButton
            radioButton?.isChecked = (i + 1) == selectedOption // Check if this option matches the selected option for the current question
        }

        optionsRG.setOnCheckedChangeListener { group, checkedId ->
            // Get the checked RadioButton's ID
            val checkedRadioButtonId = group.checkedRadioButtonId

            // Handle the selection of RadioButton
            when (checkedRadioButtonId) {
                R.id.option1RB -> Variables.questionsList[currentQuestionIndex].setUserOption(1)
                R.id.option2RB -> Variables.questionsList[currentQuestionIndex].setUserOption(2)
                R.id.option3RB -> Variables.questionsList[currentQuestionIndex].setUserOption(3)
                R.id.option4RB -> Variables.questionsList[currentQuestionIndex].setUserOption(4)
            }

            explanationTV.setText("Explanation: \n${Variables.questionsList[currentQuestionIndex].getExplanation()}")
            answerTV.setText("Right Option: ${Variables.questionsList[currentQuestionIndex].getCorrectOption()}")
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        Variables.questionsList.clear()
    }
}