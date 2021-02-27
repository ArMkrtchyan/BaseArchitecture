package com.armboldmind.grandmarket.shared.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object AppConstants {
    val LANGUAGE_CODE = stringPreferencesKey("LanguageCode")
    val DARK_MODE = booleanPreferencesKey("DarkMode")
}