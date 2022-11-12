package id.melur.gliandroidtest

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStore(private val context: Context) {

    fun getMovieId(): Flow<Int> {
        return context.dataStore.data.map { pref ->
            pref[KEY_ID] ?: 0
        }
    }

    suspend fun saveDataMovieId(id: Int) {
        context.dataStore.edit { pref ->
            pref[KEY_ID] = id
        }
    }

    suspend fun clearData() {
        context.dataStore.edit { pref ->
            pref.clear()
        }
    }


    companion object {
        private const val DATA_STORE_NAME = "movieId"
        private val KEY_ID = intPreferencesKey("key_id")
        private val Context.dataStore by preferencesDataStore(name = DATA_STORE_NAME)
    }

}