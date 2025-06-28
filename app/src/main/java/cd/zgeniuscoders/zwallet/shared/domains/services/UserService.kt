package cd.zgeniuscoders.zwallet.shared.domains.services

import cd.zgeniuscoders.zwallet.auth.domains.models.User
import cd.zgeniuscoders.zwallet.core.utils.Response
import kotlinx.coroutines.flow.Flow

interface UserService {

    fun getUser(userId: String): Flow<Response<User>>

    fun addUser(data: User): Flow<Response<Boolean>>

}