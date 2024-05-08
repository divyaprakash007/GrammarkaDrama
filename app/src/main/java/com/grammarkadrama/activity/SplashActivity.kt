package com.grammarkadrama.activity

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.grammarkadrama.R
/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 3000 // 3 seconds delay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Hide the action bar
        supportActionBar?.hide()

        // Handler to navigate to the main activity after delay
        Handler().postDelayed({
            // Start the main activity
            // TODO: Check if user already login the MainActivity else Login Activity
            startActivity(Intent(this, MainActivity::class.java))

            // Close the splash activity
            finish()
        }, SPLASH_TIME_OUT)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show()
    }
}