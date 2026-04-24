package com.association.manager.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

    fun saveLogin(memberId: Long) {
        prefs.edit().apply {
            putBoolean(Constants.PREF_IS_LOGGED_IN, true)
            putLong(Constants.PREF_LOGGED_IN_MEMBER_ID, memberId)
            apply()
        }
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean(Constants.PREF_IS_LOGGED_IN, false)

    fun getLoggedInMemberId(): Long = prefs.getLong(Constants.PREF_LOGGED_IN_MEMBER_ID, -1)

    fun logout() {
        prefs.edit().clear().apply()
    }
}
