package com.example.do_an_co_so_3.data.manager

import android.app.Application
import android.content.Context
import com.example.do_an_co_so_3.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.do_an_co_so_3.util.Constants
import com.example.do_an_co_so_3.util.Constants.USER_SETTINGS
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalUserManagerImpI @Inject constructor(
    private val context: Context
) : LocalUserManager {

    override suspend fun saveAppEntry() {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.APP_ENTRY] ?: false
        }
    }

}

private val readOnlyProperty = preferencesDataStore(name = USER_SETTINGS)

val Context.dataStore: DataStore<Preferences> by readOnlyProperty


private object PreferencesKeys {
    val APP_ENTRY = booleanPreferencesKey(name = Constants.APP_ENTRY)
}