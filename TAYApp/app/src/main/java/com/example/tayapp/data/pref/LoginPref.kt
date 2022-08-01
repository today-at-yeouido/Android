package com.example.tayapp.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tayapp.data.pref.model.UserData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class LoginPref @Inject constructor(@ApplicationContext val context: Context) {

    companion object {
        val LOGIN_USER_ACCESS_TOKEN = stringPreferencesKey("login_user_access_token")
        val LOGIN_USER_REFRESH_TOKEN = stringPreferencesKey("login_user_refresh_token")
        val LOGIN_USER_ID = stringPreferencesKey("login_user_id")
        val LOGIN_USER_EMAIL = stringPreferencesKey("login_user_email")
        val LOGIN_USER_NAME = stringPreferencesKey("login_user_name")

        var TOKEN: String = ""

    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

    suspend fun setUser(user: UserData) {
        context.dataStore.edit { settings ->
            settings[LOGIN_USER_ACCESS_TOKEN] = user.accessToken
            settings[LOGIN_USER_ACCESS_TOKEN] = user.refreshToken
            settings[LOGIN_USER_ID] = user.id
            settings[LOGIN_USER_EMAIL] = user.email
            settings[LOGIN_USER_NAME] = user.name
        }
    }

    fun getRefreshToken(): Flow<String?> {
        return context.dataStore.data.map{ it[LOGIN_USER_REFRESH_TOKEN]}

    }

    fun getUser(): Flow<UserData> {
        return context.dataStore.data.map { preference ->
            val accsesToken = preference[LOGIN_USER_ACCESS_TOKEN] ?: ""
            val refreshToken = preference[LOGIN_USER_REFRESH_TOKEN] ?: ""
            val id = preference[LOGIN_USER_ID] ?: ""
            val email = preference[LOGIN_USER_EMAIL] ?: ""
            val name = preference[LOGIN_USER_NAME] ?: "GUEST"
            TOKEN = accsesToken

            UserData(accsesToken,refreshToken, id, email, name)
        }
    }
}