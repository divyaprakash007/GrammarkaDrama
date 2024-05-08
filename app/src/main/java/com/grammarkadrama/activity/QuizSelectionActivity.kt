package com.grammarkadrama.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.grammarkadrama.R
import com.google.firebase.database.*

class QuizSelectionActivity : AppCompatActivity() {
    private lateinit var listViewQuizzes: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_selection)

        listViewQuizzes = findViewById(R.id.listViewQuizzes)

        // Retrieve the selected category from the intent
        val selectedItem = intent.getStringExtra("selectedItem")

        // Get a reference to the Firebase Realtime Database
        val databaseRef = FirebaseDatabase.getInstance().reference

        // Show progress bar
        showProgressBar()

        // Function to retrieve the number of quizzes for the selected category
        fun getNumberOfQuizzes(selectedItem: String, databaseRef: DatabaseReference) {
            val categoryRef = databaseRef.child(selectedItem)
            categoryRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val quizzesCount = dataSnapshot.childrenCount.toInt()
                        println("Number of quizzes for $selectedItem: $quizzesCount")
                        hideProgressBar()
                        showQuizzes(selectedItem, quizzesCount)
                    } else {
                        println("Category $selectedItem does not exist.")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Failed to read value: ${databaseError.toException()}")
                    hideProgressBar()
                }
            })
        }

        // Call the function to retrieve the number of quizzes for the selected category
        if (selectedItem != null) {
            getNumberOfQuizzes(selectedItem, databaseRef)
        } else {
            println("No selected category.")
        }
    }

    private fun showQuizzes(selectedItem: String, quizzesCount: Int) {
        val quizzes = mutableListOf<String>()
        for (i in 1..quizzesCount) {
            quizzes.add("Quiz $i")
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, quizzes)
        listViewQuizzes.adapter = adapter

        // Set click listener for list items to start QuizActivity with selected quiz ID
        listViewQuizzes.setOnItemClickListener { _, _, position, _ ->
            val quizId = "quiz${position + 1}" // Quiz IDs are indexed from 1
            startQuizActivity(selectedItem, quizId)
        }
    }

    private fun startQuizActivity(selectedItem: String, quizId: String) {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("selectedItem", selectedItem)
        intent.putExtra("quizId", quizId)
        startActivity(intent)
    }

    private fun showProgressBar() {
        // Find the ProgressBar view by its ID
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // Set the ProgressBar visibility to visible
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.GONE
    }
}
