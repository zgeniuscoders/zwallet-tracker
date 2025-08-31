package cd.zgeniuscoders.zwallet.modules.auth.data

import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.modules.auth.domains.models.Login
import cd.zgeniuscoders.zwallet.modules.auth.domains.models.Register
import cd.zgeniuscoders.zwallet.modules.auth.domains.services.AuthenticationService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AuthenticationServiceImpl(
    private var auth: FirebaseAuth
) : AuthenticationService {


    override fun getCurrentUserUuid(): String? {
        return auth.currentUser?.uid
    }

    override fun login(data: Login): Flow<Response<String>> = callbackFlow {
        auth.signInWithEmailAndPassword(data.email, data.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(Response.Success(data = auth.currentUser?.uid ?: ""))
                } else {
                    val errorMessage = when (val exception = task.exception) {
                        is FirebaseAuthInvalidUserException -> "No user found with this email."
                        is FirebaseAuthInvalidCredentialsException -> "Invalid password or email format."
                        is FirebaseAuthUserCollisionException -> "This user already exists."
                        else -> exception?.localizedMessage ?: "Unknown error occurred."
                    }

                    trySend(Response.Error(errorMessage))
                }
            }

        awaitClose()
    }

    override fun register(data: Register): Flow<Response<Register>> = callbackFlow {
        auth.createUserWithEmailAndPassword(data.email, data.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(Response.Success(data = data.copy(uuid = auth.currentUser?.uid)))
                } else {
                    val errorMessage = when (val exception = task.exception) {
                        is FirebaseAuthUserCollisionException -> "Email is already in use."
                        is FirebaseAuthWeakPasswordException -> "Password is too weak."
                        is FirebaseAuthInvalidCredentialsException -> "Invalid email format."
                        else -> exception?.localizedMessage ?: "Sign up failed."
                    }
                    trySend(Response.Error(errorMessage))
                }
            }

        awaitClose()
    }

    override fun logout(): Flow<Response<Boolean>> = callbackFlow {
        try {
            auth.signOut()
            trySend(
                Response.Success(true)
            )
        } catch (e: Exception) {
            trySend(
                Response.Error(e.message.toString())
            )
        }
    }
}