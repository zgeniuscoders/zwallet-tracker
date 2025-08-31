package cd.zgeniuscoders.zwallet.core.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import cd.zgeniuscoders.zwallet.core.utils.preferences
import cd.zgeniuscoders.zwallet.core.domains.services.LocalStorageService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatastoreLocalStorageServiceImpl(
    private val context: Context
) : LocalStorageService {

    override suspend fun add(data: String, key: String) {
        val key = stringPreferencesKey(key)
        context.preferences.edit { settings ->
            settings[key] = data
        }
    }

    override suspend fun add(data: Boolean, key: String) {
        val key = booleanPreferencesKey(key)
        context.preferences.edit { settings ->
            settings[key] = data
        }
    }

    override suspend fun <T> get(key: String, defaultValue: T): Flow<T> {

        var appKey = if (checkType<String>(key)) {
            stringPreferencesKey(key)
        } else if (checkType<Boolean>(key)) {
            booleanPreferencesKey(key)
        } else {
            stringPreferencesKey(key)
        }

        val prefs: Flow<T> = context.preferences.data.map { prefs ->
            (prefs[appKey] as? T) ?: defaultValue
        }


        return prefs
    }

    override suspend fun set(key: String, data: String) {
        val key = stringPreferencesKey(key)
        context.preferences.edit { settings ->
            settings[key] = data
        }
    }

    override suspend fun set(key: String, data: Boolean) {
        val key = booleanPreferencesKey(key)
        context.preferences.edit { settings ->
            settings[key] = data
        }
    }

    override suspend fun clear(key: String) {
        val key = booleanPreferencesKey(key)
        context.preferences.edit { settings ->
            settings[key] = false
        }
    }

    inline fun <reified T> checkType(value: Any): Boolean {
        return value is T
    }

}