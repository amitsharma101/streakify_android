package com.streakify.android.utils


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalPreferences @Inject constructor(context: Context) {

    val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "Streakify"
    )

    /** Store Any Any with Key */
    suspend fun <T : Any> storeValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit {
            it[key] = value
        }
    }

    /** Store Any Object with Key */
    suspend inline fun <reified T : Any> storeValue(PreferencesPair: Pair<String, T>, value: T) {
        dataStore.edit {
            it[preferencesKey<String>(PreferencesPair.first)] = Gson().toJson(value)
        }
    }

    /** Get Stored Any with Key */
    fun <T : Any> readValue(
        key: Preferences.Key<T>
    ): Flow<T?> {
        return dataStore.getFromLocalStorageAny(key)
    }

    fun <T : Any> DataStore<Preferences>.getFromLocalStorageAny(
        PreferencesKey: Preferences.Key<T>
    ) =
        data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            it[PreferencesKey]
        }


    /** Store Any Any with Key */
    suspend fun <T : Any> resetValue(key: Preferences.Key<T>, value : T) {
        dataStore.edit {
            it[key] = value
        }
    }

    /** Get Stored Object with Key */
    inline fun <reified T> readValue(
        key: Pair<String, T>
    ): Flow<T?> {
        return dataStore.getFromLocalStorage(key)
    }

    inline fun <reified T> DataStore<Preferences>.getFromLocalStorage(
        PreferencesPair: Pair<String, T>
    ) =
        data.catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            val jsonString = it[preferencesKey<String>(PreferencesPair.first)]
            try {
                Gson().fromJson(jsonString, T::class.java)
            } catch (exception: Exception) {
                null
            }
        }

    /** Preference Keys For Global Settings */
    companion object StoreConfigPreferenceKeys {
        val AUTH_TOKEN = preferencesKey<String>("AUTH_TOKEN")
        val REFRESH_TOKEN = preferencesKey<String>("REFRESH_TOKEN")
        val FIREBASE_TOKEN = preferencesKey<String>("FIREBASE_TOKEN")
        val USER_ID = preferencesKey<Int>("USER_ID")
    }


}