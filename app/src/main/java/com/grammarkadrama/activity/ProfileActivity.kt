package com.grammarkadrama.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.grammarkadrama.AppPrefs.SharedPrefs
import com.grammarkadrama.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var userEmailTextView: TextView
    private lateinit var playedQuizTextView: TextView
    private lateinit var logoutButton: Button
    private lateinit var privacyPolicyTextView: TextView
    private lateinit var aboutUsTextView: TextView
    private lateinit var shareImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        // Initialize views
        initViews()

    }

    private fun initViews() {
        userEmailTextView = findViewById(R.id.userEmailTextView)
        playedQuizTextView = findViewById(R.id.playedQuizTV)
        logoutButton = findViewById(R.id.logoutButton)
        privacyPolicyTextView = findViewById(R.id.privacyTV)
        aboutUsTextView = findViewById(R.id.aboutTV)
        shareImageView = findViewById(R.id.shareIV)

        setOnClickListeners()

        // Retrieve the played quiz number from SharedPreferences
//        val sharedPrefs = SharedPrefs.getInstance(this)
//        val playedQuizNumber = sharedPrefs.getPlayedQuizNumber()
        playedQuizTextView.setText("Total Played Quizzes : #N/A")

    }

    private fun setOnClickListeners() {
        privacyPolicyTextView.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("buttonClicked", "policyTV")
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, 0)
        }

        aboutUsTextView.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("buttonClicked", "aboutUsTV")
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, 0)
        }

        shareImageView.setOnClickListener {
            val appUrl = "https://play.google.com/store/apps/details?id=com.grammarkadrama"

            // Create an intent to share this URL
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, appUrl)

            // Start an activity with this intent and show the chooser dialog
            startActivity(Intent.createChooser(intent, "Share via"))
        }

        logoutButton.setOnClickListener {
            Toast.makeText(this, "LogOut Session", Toast.LENGTH_SHORT).show()
        }
    }
}