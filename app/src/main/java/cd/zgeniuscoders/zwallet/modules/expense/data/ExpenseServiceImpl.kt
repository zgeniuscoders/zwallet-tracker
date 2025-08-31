package cd.zgeniuscoders.zwallet.modules.expense.data

import android.util.Log
import cd.zgeniuscoders.zwallet.core.utils.Response
import cd.zgeniuscoders.zwallet.modules.expense.domain.models.Expense
import cd.zgeniuscoders.zwallet.modules.expense.domain.services.ExpenseService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ExpenseServiceImpl(
    var db: FirebaseFirestore
) : ExpenseService {

    var collection = db
        .collection("users")

    override fun getExpenses(userId: String): Flow<Response<List<Expense>>> = callbackFlow {

        try {
            collection
                .document(userId)
                .collection("expenses")
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        trySend(
                            Response.Error(message = error.message.toString())
                        )
                    }

                    if (value != null) {
                        var expenses = value.toObjects(Expense::class.java)
                        trySend(
                            Response.Success(
                                expenses
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

    override fun addExpense(data: Expense): Flow<Response<Boolean>> = callbackFlow {
        try {
            var docId = collection.document().id
            var newData = data.copy(id = docId)
            collection
                .document(data.userId)
                .collection("expenses")
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