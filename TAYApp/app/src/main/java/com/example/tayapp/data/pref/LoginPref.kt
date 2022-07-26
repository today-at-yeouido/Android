package com.example.tayapp.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tayapp.domain.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class LoginPref @Inject constructor(@ApplicationContext val context: Context) {

    companion object {
        val LOGIN_USER_TOKEN = stringPreferencesKey("login_user_token")
        val LOGIN_USER_ID = stringPreferencesKey("login_user_id")
        val LOGIN_USER_EMAIL = stringPreferencesKey("login_user_email")
        val LOGIN_USER_NAME = stringPreferencesKey("login_user_name")
    }

    suspend fun setLoginUser(user: User) {
        context.dataStore.edit { settings ->
            settings[LOGIN_USER_TOKEN] = user.token
            settings[LOGIN_USER_ID] = user.id
            settings[LOGIN_USER_EMAIL] = user.email
            settings[LOGIN_USER_NAME] = user.name
        }
    }

    fun getLoginUser(): Flow<User> {
        return context.dataStore.data.map { preference ->
            val token = preference[LOGIN_USER_TOKEN] ?: ""
            val id = preference[LOGIN_USER_ID] ?: ""
            val email = preference[LOGIN_USER_EMAIL] ?: ""
            val name = preference[LOGIN_USER_NAME] ?: "GUEST"

            User(token, id, email, name)
        }
    }
}