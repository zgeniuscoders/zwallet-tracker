package cd.zgeniuscoders.zwallet.debts.data.services

import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.debts.data.mappers.toDebtDomainList
import cd.zgeniuscoders.zwallet.debts.data.network.DebtDto
import cd.zgeniuscoders.zwallet.debts.domain.models.Debt
import cd.zgeniuscoders.zwallet.debts.domain.services.DebtService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DebtServiceImpl(
    var db: FirebaseFirestore
) : DebtService {

    var collection = db
        .collection("users")

    override fun getDebts(userId: String): Flow<Response<List<Debt>>> = callbackFlow {

        try {
            collection
                .document(userId)
                .collection("debts")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        trySend(
                            Response.Error(message = error.message.toString())
                        )
                    }

                    if (value != null) {
                        val debts = value.toObjects(DebtDto::class.java)
                        trySend(
                            Response.Success(
                                debts.toDebtDomainList()
                            )
                        )
                    }
                }
        } catch (e: Exception) {
            trySend(
                Response.Error(message = e.message.toString())
            )
        }

        awaitClose()
    }

    override fun addDebt(data: Debt): Flow<Response<Boolean>> = callbackFlow {
        try {
            var docId = collection.document().id
            var newData = data.copy(id = docId)
            collection
                .document(data.userId)
                .collection("debts")
                .document(newData.id)
                .set(newData)
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