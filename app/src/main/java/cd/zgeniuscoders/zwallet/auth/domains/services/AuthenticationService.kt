package cd.zgeniuscoders.zwallet.auth.domains.services

import cd.zgeniuscoders.zwallet.auth.domains.models.Login
import cd.zgeniuscoders.zwallet.auth.domains.models.Register
import cd.zgeniuscoders.zwallet.core.utils.Response
import kotlinx.coroutines.flow.Flow

interface AuthenticationService {

    fun getCurrentUserUuid(): String?

    fun login(data: Login): Flow<Response<String>>

    fun register(data: Register): Flow<Response<Register>>

    fun logout(): Flow<Response<Boolean>>

}