package com.grammarkadrama.activity

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grammarkadrama.R

class StartQuizActivity : AppCompatActivity() {
    private val items = arrayOf(
        "Grammar",
        "Tenses",
        "Articles",
        "Preposition",
        "Direct and Indirect Speech",
        "Voice",
        "Synonyms",
        "Antonyms",
        "One Word Substitution",
        "Idioms and Phrases",
        "Spelling Check",
        "Sentence Completion",
        "Selecting Words",
        "Completing Statements",
        "Common Error Detection",
        "Sentence Improvement",
        "Sentence Correction",
        "Sentence Formation",
        "Ordering of Words",
        "Ordering of Sentences",
        "Verbal Analogies"
    )

    // Key-value pairs for mapping position to Firebase database name
    private val keyValuePairs = mapOf(
        1 to "grammar",
        2 to "tense",
        3 to "articles",
        4 to "preposition",
        5 to "directindirect",
        6 to "voice",
        7 to "synonyms",
        8 to "antonyms",
        9 to "oneword",
        10 to "idiomphrases",
        11 to "spellingcheck",
        12 to "sentencecomplete",
        13 to "selectingword",
        14 to "completingstatements",
        15 to "commonerror",
        16 to "sentenceimprovement",
        17 to "sentencecorrection",
        18 to "sentenceformation",
        19 to "orderofwords",
        20 to "orderofsentence",
        21 to "verbalanalogies"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_quiz)

        val listView: ListView = findViewById(R.id.listView)

        // Set up the adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        // Set item click listener
        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val selectedItem = items[position]
                val correspondingValue = keyValuePairs[position + 1] // Adjusting index
                if (correspondingValue != null) {
                    val intent = Intent(this@StartQuizActivity, QuizSelectionActivity::class.java)
                    intent.putExtra("selectedItem", correspondingValue)
                    startActivity(intent)
                }
            }
    }
}