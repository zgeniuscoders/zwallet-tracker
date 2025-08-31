package cd.zgeniuscoders.zwallet.income.data.services

import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.income.domain.models.Income
import cd.zgeniuscoders.zwallet.expense.domain.services.IncomeService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class IncomeServiceImpl(
    var db: FirebaseFirestore
) : IncomeService {

    var collection = db
        .collection("users")

    override fun getIncomes(userId: String): Flow<Response<List<Income>>> = callbackFlow {

        try {
            collection
                .document(userId)
                .collection("incomes")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        trySend(
                            Response.Error(message = error.message.toString())
                        )
                    }

                    if (value != null) {
                        var debts = value.toObjects(Income::class.java)
                        trySend(
                            Response.Success(
                                debts
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

    override fun addIncome(data: Income): Flow<Response<Boolean>> = callbackFlow {
        try {
            var docId = collection.document().id
            var newIncome = data.copy(id = docId)
            collection
                .document(data.userId)
                .collection("incomes")
                .document(newIncome.id)
                .set(newIncome)
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