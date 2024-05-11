package com.grammarkadrama.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.grammarkadrama.R

class MainActivity : AppCompatActivity() {
    // TODO: rearrange the views with context menu (Setting, App Update, share app link)

    private lateinit var startQuizLL: LinearLayout
    private lateinit var studyMaterialLL: LinearLayout
    private lateinit var premiumLL: LinearLayout
    private lateinit var wordOfTheDayLL: LinearLayout
    private lateinit var profileLL: LinearLayout
    private lateinit var toolbar: Toolbar
 //    val shareAppTV: TextView = findViewById(R.id.shareAppTV)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeAllVariables()

        setSupportActionBar(toolbar)

        startQuizLL.setOnClickListener {
            // Create an intent to StartQuizActivity
            val intent = Intent(this, StartQuizActivity::class.java)
            startActivity(intent)
        }

        studyMaterialLL.setOnClickListener {
            Toast.makeText(this, "Section is Under Development", Toast.LENGTH_LONG).show()
            // Create an intent to StartQuizActivity
//            val intent = Intent(this, StudyMaterialActivity::class.java)
//            startActivity(intent)
        }

        premiumLL.setOnClickListener {
            Toast.makeText(this, "Section is Under Development", Toast.LENGTH_LONG).show()
            // Create an intent to StartQuizActivity
//            val intent = Intent(this, GovtPremiumActivity::class.java)
//            startActivity(intent)
        }

        wordOfTheDayLL.setOnClickListener {
//            Toast.makeText(this, "Section is Under Development", Toast.LENGTH_LONG).show()
            // Create an intent to StartQuizActivity
            val intent = Intent(this, WordOfTheDayActivity::class.java)
            startActivity(intent)
        }

        profileLL.setOnClickListener {
            // Create an intent to StartQuizActivity
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_options -> {
                val view = findViewById<View>(item.itemId)
                showPopupMenu(view)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.context_menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.check_update -> {
//                    Toast.makeText(this, "Check Update clicked", Toast.LENGTH_SHORT).show()
                    val appPackageName = packageName
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                    } catch (e: android.content.ActivityNotFoundException) {
                        // If Play Store app is not installed, open the Play Store website
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                    }
                    true
                }

                R.id.rate_app -> {
//                    Toast.makeText(this, "Share App clicked", Toast.LENGTH_SHORT).show()
                    // Create an intent to open the Play Store with your app's page
                    val appPackageName = packageName
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                    } catch (e: android.content.ActivityNotFoundException) {
                        // If Play Store app is not installed, open the Play Store website
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
                    }
                    true
                }

                R.id.share_app -> {
//                    Toast.makeText(this, "Rate App clicked", Toast.LENGTH_SHORT).show()
//                  Get the Play Store URL of your app
                    val appUrl = "https://play.google.com/store/apps/details?id=com.grammarkadrama"

                    // Create an intent to share this URL
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/plain"
                    intent.putExtra(Intent.EXTRA_TEXT, appUrl)

                    // Start an activity with this intent and show the chooser dialog
                    startActivity(Intent.createChooser(intent, "Share via"))
                    true
                }
                R.id.about_us -> {
//                    Toast.makeText(this, "Rate App clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, WebViewActivity::class.java)
                    intent.putExtra("buttonClicked", "aboutUsTV")
                    startActivity(intent)
                    true
                }
                R.id.privacy_policy -> {
//                    Toast.makeText(this, "Rate App clicked", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, WebViewActivity::class.java)
                    intent.putExtra("buttonClicked", "policyTV")
                     startActivity(intent)
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }


    fun initializeAllVariables() {
        startQuizLL = findViewById(R.id.startQuizLL)
        studyMaterialLL = findViewById(R.id.studyMaterialLL)
        premiumLL = findViewById(R.id.premiumLL)
        wordOfTheDayLL = findViewById(R.id.wordOfTheDayLL)
        profileLL = findViewById(R.id.profileLL)
        toolbar = findViewById<Toolbar>(R.id.toolbar)

    }
}
