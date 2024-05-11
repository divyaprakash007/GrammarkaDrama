package com.grammarkadrama.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grammarkadrama.DataHandler.Variables
import com.grammarkadrama.R

class ResultActivity : AppCompatActivity() {

    val motivationalLines = arrayOf(
        "Zarurat hai, hosla hai, hausla hai, chalte raho, kuch na kuch toh milega.",
        "Jab tak raaste khud nahi mil jaate, chalte raho, manzil khud hi mil jaayegi.",
        "Hausle buland rakh, mushkilein tujhe harayengi nahi.",
        "Kabhi haar mat mano, kismat bhi tere saath hai.",
        "Sapne wo nahi jo hum sote waqt dekhte hain, sapne wo hain jo hamein sone nahi dete.",
        "Raaste toh mushkil hai, par manzil paane ka maza hi kuch aur hai.",
        "Himmat kar, saamna kar, safalta tere kadam chumegi.",
        "Koshish karne walon ki kabhi haar nahi hoti.",
        "Andhera ho ya ujala, bas himmat mat haaro.",
        "Chaahe jo bhi ho, uska saamna karo, har mushkil aasan ho jaayegi."
    )
    var scoreCounter: Int = 0
//    val restartButton: Button = findViewById(R.id.restartButton)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val motivationalTV: TextView = findViewById(R.id.motivationalTV)
        val quizScoreTV: TextView = findViewById(R.id.quizScoreTV)
        val sharelinkBtn: Button = findViewById(R.id.sharelinkBtn)

        // Generate a random index
        val randomIndex = (0 until motivationalLines.size).random()

        // Set the text of the TextView with the motivational line at the random index
        motivationalTV.text = motivationalLines[randomIndex]

        for (i in 0 until Variables.questionsList.size) {
               if (Variables.questionsList[i].getCorrectOption() == Variables.questionsList[i].getUserOption()){
                   scoreCounter++
               }
        }

        quizScoreTV.setText("Quiz Score is : ${scoreCounter}")

        sharelinkBtn.setOnClickListener {
            // Get the Play Store URL of your app
            val appUrl = "https://play.google.com/store/apps/details?id=com.grammarkadrama"

            // Create an intent to share this URL
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, appUrl)

            // Start an activity with this intent and show the chooser dialog
            startActivity(Intent.createChooser(intent, "Share via"))
        }

//        restartButton.setOnClickListener {
//            // Create Intent to navigate to MainActivity
//            val intent = Intent(this, MainActivity::class.java)
//
//            // Start the activity using the intent
//            startActivity(intent)
//        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}