package cd.zgeniuscoders.zwallet.core.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.preferences: DataStore<Preferences> by preferencesDataStore(name = "zwallet_tracker")