package com.armboldmind.grandmarket.shared.managers

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

class DataStoreManager(val context: Context) {
    val Context.dataStore by preferencesDataStore(name = "Settings_pref", scope = CoroutineScope(Dispatchers.IO + Job()))

    fun <T> saveByKey(key: Preferences.Key<T>, value: T) {
        CoroutineScope(Dispatchers.IO + Job()).launch {
            context.dataStore.edit { settings ->
                settings[key] = value
            }
        }
    }

    inline fun <reified T> findByKey(key: Preferences.Key<T>): Flow<T?> {
        return context.dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else throw  it
        }.map { settings ->
            settings[key]
        }
    }
}
