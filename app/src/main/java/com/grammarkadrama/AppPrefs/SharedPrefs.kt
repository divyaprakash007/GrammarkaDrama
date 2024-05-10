package com.grammarkadrama.AppPrefs
import android.content.Context
import android.content.SharedPreferences

class SharedPrefs private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        val SHARED_PREFERENCES_NAME = "shared"
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun setPlayedQuizNumber(quizNumber: Int) {
        editor.putInt(PLAYED_QUIZ_NUMBER, quizNumber)
        editor.apply()
    }

    fun getPlayedQuizNumber(): Int {
        return sharedPreferences.getInt(PLAYED_QUIZ_NUMBER, 0)
    }

    fun setUserLoggedIn(isLoggedIn: Boolean) {
        editor.putBoolean(USER_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(USER_LOGGED_IN, false)
    }

    companion object {
        private var instance: SharedPrefs? = null
        private const val PLAYED_QUIZ_NUMBER = "played_quiz_number"
        private const val USER_LOGGED_IN = "user_logged_in"

        fun getInstance(context: Context): SharedPrefs {
            if (instance == null) {
                instance = SharedPrefs(context.applicationContext)
            }
            return instance as SharedPrefs
        }
    }
}

