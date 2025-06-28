package cd.zgeniuscoders.zwallet.shared.domains.services

import kotlinx.coroutines.flow.Flow

interface LocalStorageService {

    suspend fun add(data: String, key: String)

    suspend fun add(data: Boolean, key: String)

    suspend fun <T> get(key: String, defaultValue: T): Flow<T>

    suspend fun set(key: String, data: String)

    suspend fun set(key: String, data: Boolean)

    suspend fun clear(key: String)

}