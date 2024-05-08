package com.grammarkadrama.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.grammarkadrama.R

class MainActivity : AppCompatActivity() {

    private lateinit var listViewItems: ListView
    private lateinit var textViewMessage: TextView
    private lateinit var appUpdateManager: com.google.android.play.core.appupdate.AppUpdateManager

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
        setContentView(R.layout.activity_main)

        // Initialize the app update manager
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)

        // Check for app update availability
        checkForAppUpdate()

        listViewItems = findViewById(R.id.listViewItems)
        textViewMessage = findViewById(R.id.textViewMessage)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listViewItems.adapter = adapter

        listViewItems.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                // Get the selected item value
                val selectedItem = items[position]

                // Get the corresponding Firebase database name
                val value = keyValuePairs[position + 1] // Adjust position

                // Check if the value exists in the map
                if (value != null) {
                    // Start the QuizActivity and pass the selected item value
                    val intent = Intent(this, QuizSelectionActivity::class.java)
                    intent.putExtra("selectedItem", value)
                    startActivity(intent)
                } else {
                    // Handle the case when the value does not exist in the map
                    // (This should not occur if the map is correctly initialized)
                    // Log.e(TAG, "No value found for position: $position")
                }
            }

        // Show the message if no item is selected
        if (listViewItems.selectedItem == null) {
            textViewMessage.visibility = View.VISIBLE
        }
    }

    private fun checkForAppUpdate() {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                // An update is available, show the update dialog
                showUpdateDialog()
            }
        }
    }

    private fun showUpdateDialog() {
        AlertDialog.Builder(this)
            .setTitle("Update Available")
            .setMessage("A new version of the app is available. Update now?")
            .setPositiveButton("Update") { _, _ ->
                startAppUpdate()
            }
            .setNegativeButton("Not Now") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun startAppUpdate() {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                // Start a flexible update
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    this,
                    REQUEST_CODE_APP_UPDATE
                )
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                // Update failed or was canceled by the user
                // Retry or handle accordingly
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_APP_UPDATE = 1000
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        // Disable back button press (Do nothing)
    }
}
