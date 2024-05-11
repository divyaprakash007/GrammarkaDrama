package com.grammarkadrama.activity

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.grammarkadrama.AppPrefs.SharedPrefs
import com.grammarkadrama.DataHandler.Variables
import com.grammarkadrama.Model.QuizQuestionsModel
import com.grammarkadrama.R
import java.util.Locale

class WordOfTheDayActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var progressDialog: AlertDialog? = null
    private lateinit var databaseRef: DatabaseReference
    private lateinit var textToSpeech: TextToSpeech
    private var wordOfTheDayNumber: Int? = null
    private lateinit var sharedPrefs: SharedPrefs
    private lateinit var defHindiTV: TextView
    private lateinit var partOfSpeechTV: TextView
    private lateinit var exampleTV: TextView
    private lateinit var anotymnTV: TextView
    private lateinit var synonymTV: TextView
    private lateinit var wordTV: TextView
    private lateinit var ttsIV: ImageView
    private lateinit var textToSpeak: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_word_of_the_day)
        sharedPrefs = SharedPrefs.getInstance(this)
        wordOfTheDayNumber = sharedPrefs.getWordOfDayNumber()

        textToSpeech = TextToSpeech(this, this)
        defHindiTV = findViewById(R.id.defHindiTV)
        partOfSpeechTV = findViewById(R.id.partOfSpeechTV)
        exampleTV = findViewById(R.id.exampleTV)
        anotymnTV = findViewById(R.id.anotymnTV)
        synonymTV = findViewById(R.id.synonymTV)
        wordTV = findViewById(R.id.wordTV)
        ttsIV = findViewById(R.id.ttsIV)

        databaseRef = FirebaseDatabase.getInstance().reference
        fetchDataFromFirebase()

        ttsIV.setOnClickListener {
            textToSpeak = wordTV.text.toString()
            textToSpeech.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }



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

    private fun fetchDataFromFirebase() {
        showProgressDialog()
        // Construct Firebase Database reference path for the selected category and quiz ID
        val dataRef = databaseRef.child("words").child(""+wordOfTheDayNumber)

        // Attach ValueEventListener to retrieve data
        dataRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                hideProgressDialog()
                if (dataSnapshot.exists()) {
                    sharedPrefs.setWordOfDayNumber((wordOfTheDayNumber as Int) + 1)
                    wordTV.text = dataSnapshot.child("word").value as String
                    defHindiTV.text = "Definition : " + dataSnapshot.child("definition_hindi").value as String
                    partOfSpeechTV.text = "Part of Speech : " +dataSnapshot.child("part_of_speech").value as String
                    exampleTV.text = "Example : \n" +dataSnapshot.child("example_sentence").value as String
                    val antonymsList = mutableListOf<String>()
                    val synonymsList = mutableListOf<String>()

            // Iterate over each child node of "antonyms" array
                    dataSnapshot.child("antonyms").children.forEach { antonymSnapshot ->
                        // Extract value from the current child node and add it to the list
                        val antonym = antonymSnapshot.getValue(String::class.java)
                        antonym?.let { antonymsList.add(it) }
                    }

// Concatenate all antonyms into a single string
                    val antonymsString = antonymsList.joinToString(", ")
                    anotymnTV.text = "Antonyms : \n" +antonymsString

                    // Iterate over each child node of "antonyms" array
                    dataSnapshot.child("synonyms").children.forEach { synonymSnapshot ->
                        // Extract value from the current child node and add it to the list
                        val synonyms = synonymSnapshot.getValue(String::class.java)
                        synonyms?.let { synonymsList.add(it) }
                    }

// Concatenate all antonyms into a single string
                    val synonymsString = synonymsList.joinToString(", ")

                    synonymTV.text = "Synonyms : \n"+synonymsString

                    Log.d("TAG", "onDataChange word of the day: ${dataSnapshot.child("word").value}")
//                    textToSpeech.speak(""+dataSnapshot.child("word").value, TextToSpeech.QUEUE_FLUSH, null, null)
                } else {
                    sharedPrefs.setWordOfDayNumber(0)
                    Log.d("FirebaseData", "No data found for the selected category and quiz ID.")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                hideProgressDialog()
                sharedPrefs.setWordOfDayNumber(0)
                Log.e("FirebaseData", "Database error occurred: ${databaseError.message}")
            }
        })
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not supported", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Initialization failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }


}