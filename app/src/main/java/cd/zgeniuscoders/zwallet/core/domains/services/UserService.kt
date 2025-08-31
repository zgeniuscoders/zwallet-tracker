package cd.zgeniuscoders.zwallet.core.domains.services

import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.modules.auth.domains.models.User
import kotlinx.coroutines.flow.Flow

interface UserService {

    fun getUser(userId: String): Flow<Response<User>>

    fun addUser(data: User): Flow<Response<Boolean>>

}