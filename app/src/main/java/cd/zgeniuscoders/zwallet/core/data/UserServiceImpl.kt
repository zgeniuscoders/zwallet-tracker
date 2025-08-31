package cd.zgeniuscoders.zwallet.core.data

import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.core.domains.services.UserService
import cd.zgeniuscoders.zwallet.modules.auth.domains.models.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserServiceImpl(
    var db: FirebaseFirestore
) : UserService {

    var collection = "users"
    override fun getUser(userId: String): Flow<Response<User>> = callbackFlow {
        try {
            db
                .collection(collection)
                .document(userId)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        trySend(
                            Response.Error(message = error.message.toString())
                        )
                    }

                    if (value != null) {
                        var user = value.toObject(User::class.java)
                        trySend(
                            Response.Success(user)
                        )
                    }
                }
        } catch (e: Exception) {
            trySend(
                Response.Error(
                    message = e.message.toString()
                )
            )
        }
        awaitClose()
    }

    override fun addUser(data: User): Flow<Response<Boolean>> = callbackFlow {
        try {
            db
                .collection(collection)
                .document(data.id)
                .set(data)
                .addOnFailureListener {
                    trySend(Response.Error(message = it.message.toString()))
                }.addOnSuccessListener {
                    trySend(Response.Success(true))
                }
        } catch (e: Exception) {
            trySend(
                Response.Error(message = e.message.toString())
            )
        }
        awaitClose()
    }
}