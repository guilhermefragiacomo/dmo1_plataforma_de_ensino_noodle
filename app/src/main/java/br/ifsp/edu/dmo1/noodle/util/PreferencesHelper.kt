package br.ifsp.edu.dmo1.noodle.util

import android.content.Context

class PreferencesHelper(context : Context) {
    companion object{
        const val FILE_NAME = "NoodlePrefs"
        const val KEY_NAME = "SessionId"
    }

    private val sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    fun saveSessionId(sessionId: String) {
        sharedPreferences.edit().putString(KEY_NAME, sessionId).apply()
    }

    fun getSessionId(): String? {
        return sharedPreferences.getString(KEY_NAME, null)
    }
}